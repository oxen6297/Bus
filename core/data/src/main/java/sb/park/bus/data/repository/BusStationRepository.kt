package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult
import sb.park.model.response.bus.BusSearchResponse
import sb.park.model.response.bus.BusStationResponse

interface BusStationRepository {
    fun getData(busId: String): Flow<ApiResult<List<BusStationResponse>>>

    fun getSearch(busId: String): Flow<ApiResult<List<BusSearchResponse>>>
}