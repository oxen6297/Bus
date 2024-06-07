package sb.park.bus.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import sb.park.bus.data.AppDispatchers
import sb.park.bus.data.Dispatcher
import sb.park.bus.data.mapper.toData
import sb.park.bus.data.service.BusIdService
import sb.park.bus.data.service.BusService
import sb.park.bus.data.util.toList
import sb.park.model.ApiResult
import sb.park.model.response.bus.BusSearchResponse
import sb.park.model.response.bus.BusStationResponse
import sb.park.model.safeFlow
import javax.inject.Inject

internal class BusSearchRepositoryImpl @Inject constructor(
    private val busService: BusService,
    private val busIdService: BusIdService,
    @Dispatcher(AppDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher
) : BusSearchRepository {

    override fun getSearch(busNumber: String): Flow<ApiResult<List<BusSearchResponse>>> = safeFlow {

        busNumber.ifEmpty { return@safeFlow emptyList<BusSearchResponse>() }

        val busIdList = busIdService.getBusId().asSequence().distinctBy {
            it.routeId
        }.filter {
            it.routeName.startsWith(busNumber, ignoreCase = true)
        }.toList()

        mutableListOf<BusSearchResponse>().apply {
            busIdList.map { id ->
                busService.getStation(
                    busRouteId = id.routeId.toString()
                ).msgBody.itemList.toList<BusStationResponse>().distinctBy {
                    it.direction
                }.toData(id.routeId.toString()).map {
                    add(it)
                }
            }
        }
    }.flowOn(coroutineDispatcher)
}