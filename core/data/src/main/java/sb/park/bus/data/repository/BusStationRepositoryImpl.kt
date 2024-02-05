package sb.park.bus.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import sb.park.model.ApiResult
import sb.park.bus.data.AppDispatchers
import sb.park.bus.data.Dispatcher
import sb.park.bus.data.mapper.toData
import sb.park.bus.data.mapper.toSearch
import sb.park.model.response.BusSearchResponse
import sb.park.model.response.BusStationResponse
import sb.park.model.safeFlow
import sb.park.bus.data.service.BusStationService
import sb.park.bus.data.util.toList
import javax.inject.Inject

class BusStationRepositoryImpl @Inject constructor(
    private val busStationService: BusStationService,
    @Dispatcher(AppDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher
) : BusStationRepository {
    override fun getData(busId: String): Flow<ApiResult<List<BusStationResponse>>> = safeFlow {
        busStationService.getData(busRouteId = busId).msgBody.itemList.toList<BusStationResponse>()
            .map {
                it.toData()
            }.distinctBy {
                it.direction
            }
    }.flowOn(coroutineDispatcher)

    override fun getSearch(busId: String): Flow<ApiResult<List<BusSearchResponse>>> = safeFlow {
        busStationService.getData(busRouteId = busId).msgBody.itemList.toList<BusStationResponse>()
            .map {
                it.toData()
            }.distinctBy {
                it.direction
            }.toSearch(busId).toList()
    }.flowOn(coroutineDispatcher)
}