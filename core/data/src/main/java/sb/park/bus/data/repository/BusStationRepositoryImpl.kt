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
import sb.park.model.safeFlow
import javax.inject.Inject

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
                entity.station == it.stationId
            }

            it.toData(isFavorite, locationList) {
                CoroutineScope(coroutineDispatcher).launch {
                    if (isFavorite) {
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
}