package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult
import sb.park.model.response.bus.BusSearchResponse
import sb.park.model.response.bus.BusStationResponse
import sb.park.model.response.bus.DeliveryData

interface BusStationRepository {
    fun getData(deliveryData: DeliveryData): Flow<ApiResult<List<BusStationResponse>>>

    fun getSearch(busId: String): Flow<ApiResult<List<BusSearchResponse>>>
}