package sb.park.bus.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import sb.park.bus.data.AppDispatchers
import sb.park.bus.data.Dispatcher
import sb.park.bus.data.mapper.toData
import sb.park.bus.data.mapper.toSearch
import sb.park.bus.data.service.BusLocationService
import sb.park.bus.data.service.BusStationService
import sb.park.bus.data.util.toList
import sb.park.model.ApiResult
import sb.park.model.response.bus.BusLocationResponse
import sb.park.model.response.bus.BusSearchResponse
import sb.park.model.response.bus.BusStationResponse
import sb.park.model.response.bus.DeliveryData
import sb.park.model.safeFlow
import javax.inject.Inject

class BusStationRepositoryImpl @Inject constructor(
    private val busStationService: BusStationService,
    private val busLocationService: BusLocationService,
    private val favoriteRepository: FavoriteRepository,
    @Dispatcher(AppDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher
) : BusStationRepository {

    override fun getData(
        deliveryData: DeliveryData
    ): Flow<ApiResult<List<BusStationResponse>>> = safeFlow {

        val locationList = busLocationService.getData(
            busRouteId = deliveryData.busId
        ).msgBody.itemList.toList<BusLocationResponse>().map {
            it.toData()
        }

        busStationService.getData(
            busRouteId = deliveryData.busId
        ).msgBody.itemList.toList<BusStationResponse>().map {
            it.toData(
                isFavorite(it.stationId),
                isHere(locationList, it.section)) {
                CoroutineScope(coroutineDispatcher).launch {
                    if (isFavorite(it.stationId)) {
                        favoriteRepository.deleteStationFavorite(it.stationId)
                    } else {
                        favoriteRepository.insertFavorite(
                            deliveryData.toFavorite(
                                DeliveryData.Type.STATION.type,
                                it.stationId,
                                it.stationNm
                            )
                        )
                    }
                }
            }
        }
    }.flowOn(coroutineDispatcher)

    override fun getSearch(busId: String): Flow<ApiResult<List<BusSearchResponse>>> = safeFlow {
        busStationService.getData(
            busRouteId = busId
        ).msgBody.itemList.toList<BusStationResponse>().distinctBy {
            it.direction
        }.map {
            it.toData()
        }.toSearch(busId).toList()
    }.flowOn(coroutineDispatcher)

    private suspend fun isFavorite(stationId: String): Boolean {
        return favoriteRepository.getFavorite().any {
            it.station == stationId
        }
    }

    private fun isHere(locationList: List<BusLocationResponse>, sectionId: String): Boolean {
        return locationList.any {
            it.sectionId == sectionId
        }
    }
}