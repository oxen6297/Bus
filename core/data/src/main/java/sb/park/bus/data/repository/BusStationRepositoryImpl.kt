package sb.park.bus.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
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

        val locationList = busLocationService.getData(
            busRouteId = argumentData.busId
        ).msgBody.itemList.toList<BusLocationResponse>().map {
            it.toData()
        }

        busStationService.getData(
            busRouteId = argumentData.busId
        ).msgBody.itemList.toList<BusStationResponse>().map {

            it.toData(isFavorite(it.stationId), locationList) {
                CoroutineScope(coroutineDispatcher).launch {
                    if (isFavorite(it.stationId)) {
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
    ): Flow<String> = flow {
        val stationList = busStationService.getData(
            busRouteId = argumentData.busId
        ).msgBody.itemList.toList<BusStationResponse>()

        val nearStation = stationList.minBy {
            val distanceX = Math.toRadians(it.gpsX.toDouble() - latitude)
            val distanceY = Math.toRadians(it.gpsY.toDouble() - longitude)
            val distanceA = sin(distanceX / 2).pow(2.0) + sin(distanceY / 2).pow(2.0) *
                    cos(Math.toRadians(latitude)) * cos(Math.toRadians(it.gpsX.toDouble()))
            val distanceB = 2 * asin(sqrt(distanceA))
            (6372.8 * 1000 * distanceB).toInt()
        }

        emit(nearStation.stationId)
    }

    private suspend fun isFavorite(stationId: String): Boolean {
        return favoriteDao.getFavorite().any {
            it.station == stationId
        }
    }
}