package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.ApiResult
import sb.park.bus.data.response.BusIdResponse

interface BusIdRepository {
    fun getData(busNumber: String): Flow<ApiResult<List<BusIdResponse>>>
}