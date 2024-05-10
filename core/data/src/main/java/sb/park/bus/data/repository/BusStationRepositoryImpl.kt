package sb.park.bus.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import sb.park.bus.data.AppDispatchers
import sb.park.bus.data.Dispatcher
import sb.park.bus.data.mapper.toData
import sb.park.bus.data.room.FavoriteDao
import sb.park.bus.data.service.BusLocationService
import sb.park.bus.data.service.BusStationService
import sb.park.bus.data.util.toList
import sb.park.model.ApiResult
import sb.park.model.response.bus.ArgumentData
import sb.park.model.response.bus.BusLocationResponse
import sb.park.model.response.bus.BusStationResponse
import sb.park.model.response.bus.LocationModel
import sb.park.model.safeFlow
import javax.inject.Inject
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

internal class BusStationRepositoryImpl @Inject constructor(
    private val busStationService: BusStationService,
    private val busLocationService: BusLocationService,
    private val favoriteDao: FavoriteDao,
    @Dispatcher(AppDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher
) : BusStationRepository {

    override fun getData(
        argumentData: ArgumentData
    ): Flow<ApiResult<List<BusStationResponse>>> = safeFlow {

        val busId = argumentData.busId
        val locationList = busLocationService.getData(
            busRouteId = busId
        ).msgBody.itemList.toList<BusLocationResponse>().map {
            it.toData()
        }

        busStationService.getData(
            busRouteId = busId
        ).msgBody.itemList.toList<BusStationResponse>().map {

            it.toData(isFavorite(it.stationId, busId), locationList) {
                CoroutineScope(coroutineDispatcher).launch {
                    if (isFavorite(it.stationId, busId)) {
                        favoriteDao.deleteStationFavorite(it.stationId)
                    } else {
                        favoriteDao.insertFavorite(
                            argumentData.toFavorite(
                                ArgumentData.Type.STATION.type,
                                it.stationId,
                                it.stationNm
                            )
                        )
                    }
                }
            }
        }
    }.flowOn(coroutineDispatcher)

    override fun getLocation(
        argumentData: ArgumentData,
        latitude: Double,
        longitude: Double
    ): Flow<LocationModel> = flow {
        val stationList = busStationService.getData(
            busRouteId = argumentData.busId
        ).msgBody.itemList.toList<BusStationResponse>()

        val locationModel = LocationModel()

        /** 좌표 사이 거리 계산*/
        val nearStation = stationList.minBy {
            val distanceX = Math.toRadians(it.gpsY.toDouble() - latitude)
            val distanceY = Math.toRadians(it.gpsX.toDouble() - longitude)
            val distanceA = sin(distanceX / 2).pow(2.0) +
                    sin(distanceY / 2).pow(2.0) *
                    cos(Math.toRadians(latitude)) *
                    cos(Math.toRadians(it.gpsY.toDouble()))
            val distanceB = 2 * asin(sqrt(distanceA))
            val distance = (R * distanceB).toInt()

            distance.apply { locationModel.distance = this }
        }

        locationModel.position = stationList.indexOf(nearStation)
        emit(locationModel)

    }.catch {
        it.printStackTrace()
    }.flowOn(coroutineDispatcher)

    private suspend fun isFavorite(stationId: String, busId: String): Boolean {
        return favoriteDao.getFavorite().any {
            it.station == stationId && it.busId == busId
        }
    }

    companion object {
        private const val R = 6372.8 * 1000
    }
}