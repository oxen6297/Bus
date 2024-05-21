package sb.park.bus.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
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

        val locationList = busLocationService.getData(
            busRouteId = argumentData.busId
        ).msgBody.itemList.toList<BusLocationResponse>().map {
            it.toData()
        }

        busStationService.getData(
            busRouteId = argumentData.busId
        ).msgBody.itemList.toList<BusStationResponse>().map {
            val isFavorite = favoriteDao.getFavorite().any { entity ->
                entity.station == it.stationId && entity.busId == argumentData.busId
            }
            it.toData(isFavorite, locationList) {
                CoroutineScope(coroutineDispatcher).launch {
                    favoriteDao.addFavorite(it, argumentData)
                }
            }
        }
    }.flowOn(coroutineDispatcher)

    override fun getNearStation(
        argumentData: ArgumentData,
        latitude: Double,
        longitude: Double
    ): Flow<ApiResult<LocationModel>> = safeFlow {
        val stationList = busStationService.getData(
            busRouteId = argumentData.busId
        ).msgBody.itemList.toList<BusStationResponse>()

        LocationModel().apply {
            stationList.minBy { response ->
                val latDiff = Math.toRadians(response.gpsY.toDouble() - latitude)
                val lonDiff = Math.toRadians(response.gpsX.toDouble() - longitude)
                val calculateDiff = sin(latDiff / 2).pow(2.0) +
                        sin(lonDiff / 2).pow(2.0) *
                        cos(Math.toRadians(latitude)) *
                        cos(Math.toRadians(response.gpsY.toDouble()))
                val distance = (2 * asin(sqrt(calculateDiff)) * R).toInt()

                distance.also { this.distance = it }
            }.also { position = stationList.indexOf(it) }
        }
    }.flowOn(coroutineDispatcher)

    companion object {
        private const val R = 6372.8 * 1000
    }
}