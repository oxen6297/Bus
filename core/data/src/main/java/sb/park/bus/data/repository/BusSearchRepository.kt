package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult
import sb.park.model.response.bus.BusSearchResponse

interface BusSearchRepository {

    fun getSearch(busNumber: String): Flow<ApiResult<List<BusSearchResponse>>>
}