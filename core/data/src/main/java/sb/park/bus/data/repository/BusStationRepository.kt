package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult
import sb.park.model.response.BusSearchResponse
import sb.park.model.response.BusStationResponse

interface BusStationRepository {
    fun getData(busId: String): Flow<ApiResult<List<BusStationResponse>>>

    fun getSearch(busId: String): Flow<ApiResult<Set<BusSearchResponse>>>
}