package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult
import sb.park.model.response.BusLocationResponse

interface BusLocationRepository {
    fun getData(busId: String): Flow<ApiResult<List<BusLocationResponse>>>
}