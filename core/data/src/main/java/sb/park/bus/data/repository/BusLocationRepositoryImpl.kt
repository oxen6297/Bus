package sb.park.bus.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import sb.park.bus.data.AppDispatchers
import sb.park.bus.data.Dispatcher
import sb.park.bus.data.mapper.toData
import sb.park.model.response.BusLocationResponse
import sb.park.bus.data.service.BusLocationService
import sb.park.model.ApiResult
import sb.park.model.safeFlow
import javax.inject.Inject

internal class BusLocationRepositoryImpl @Inject constructor(
    private val busLocationService: BusLocationService,
    @Dispatcher(AppDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher
) : BusLocationRepository {

    override fun getData(busId: String): Flow<ApiResult<List<BusLocationResponse>>> = safeFlow {
        busLocationService.getData(busRouteId = busId).map {
            it.toData()
        }
    }.flowOn(coroutineDispatcher)
}