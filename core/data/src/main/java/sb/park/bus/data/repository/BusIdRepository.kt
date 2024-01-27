package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult
import sb.park.model.response.BusIdResponse

interface BusIdRepository {
    fun getData(busNumber: String): Flow<ApiResult<List<BusIdResponse>>>
}