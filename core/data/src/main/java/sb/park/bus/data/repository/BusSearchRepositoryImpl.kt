package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.mapper.toData
import sb.park.bus.data.mapper.toSearch
import sb.park.bus.data.service.BusIdService
import sb.park.bus.data.service.BusStationService
import sb.park.bus.data.util.toList
import sb.park.model.ApiResult
import sb.park.model.response.bus.BusIdResponse
import sb.park.model.response.bus.BusSearchResponse
import sb.park.model.response.bus.BusStationResponse
import sb.park.model.safeFlow
import javax.inject.Inject

internal class BusSearchRepositoryImpl @Inject constructor(
    private val busStationService: BusStationService,
    private val busIdService: BusIdService
) : BusSearchRepository {

    override fun getSearch(busNumber: String): Flow<ApiResult<List<BusSearchResponse>>> = safeFlow {
        val busIdList = busIdService.getBusId().toList<BusIdResponse>().asSequence().distinctBy {
            it.routeId
        }.filter {
            it.routeName.startsWith(busNumber, ignoreCase = true)
        }.toList()

        mutableListOf<BusSearchResponse>().apply {
            busIdList.map { id ->
                busStationService.getData(
                    busRouteId = id.routeId.toString()
                ).msgBody.itemList.toList<BusStationResponse>().distinctBy {
                    it.direction
                }.map {
                    it.toData()
                }.toSearch(id.routeId.toString()).map {
                    add(it)
                }
            }
        }
    }
}