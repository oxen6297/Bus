package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.ApiResult
import sb.park.bus.data.response.BusSearchResponse
import sb.park.bus.data.response.BusStationResponse

interface BusStationRepository {
    suspend fun getData(busId: String): Flow<ApiResult<List<BusStationResponse>>>

    suspend fun getSearch(busId: String): Flow<ApiResult<List<BusSearchResponse>>>
}