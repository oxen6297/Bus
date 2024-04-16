package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult
import sb.park.model.response.bus.BusStationResponse
import sb.park.model.response.bus.ArgumentData

interface BusStationRepository {
    fun getData(argumentData: ArgumentData): Flow<ApiResult<List<BusStationResponse>>>
}