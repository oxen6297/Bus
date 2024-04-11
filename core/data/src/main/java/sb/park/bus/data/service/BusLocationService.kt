package sb.park.bus.data.service

import retrofit2.http.GET
import retrofit2.http.Query
import sb.park.bus.data.util.API_KEY
import sb.park.bus.data.util.BUS_LOCATION_SERVICE
import sb.park.bus.data.util.RESULT_TYPE
import sb.park.model.response.bus.BusResponse

internal interface BusLocationService {
    @GET(BUS_LOCATION_SERVICE)
    suspend fun getData(
        @Query("busRouteId") busRouteId: String,
        @Query("serviceKey") serviceKey: String = API_KEY,
        @Query("resultType") resultType: String = RESULT_TYPE
    ): BusResponse
}