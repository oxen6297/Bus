package sb.park.bus.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import sb.park.bus.data.ApiResult
import sb.park.bus.data.AppDispatchers
import sb.park.bus.data.Dispatcher
import sb.park.bus.data.response.BusIdResponse
import sb.park.bus.data.safeFlow
import sb.park.bus.data.service.BusIdService
import sb.park.bus.data.util.toList
import javax.inject.Inject

internal class BusIdRepositoryImpl @Inject constructor(
    private val busIdService: BusIdService,
    @Dispatcher(AppDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher
) : BusIdRepository {
    override fun getData(busNumber: String): Flow<ApiResult<List<BusIdResponse>>> = safeFlow {
        busIdService.getBusId().toList<BusIdResponse>().filter {
            it.routeName.startsWith(busNumber)
        }.distinctBy {
            it.routeId
        }
    }.flowOn(coroutineDispatcher)
}