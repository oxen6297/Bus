package sb.park.bus.data.service

import retrofit2.http.GET
import retrofit2.http.Query
import sb.park.bus.data.response.BusLocationResponse
import sb.park.bus.data.util.API_KEY

interface BusLocationService {
    @GET("buspos/getBusPosByRtidList")
    suspend fun getData(
        @Query("serviceKey") serviceKey: String = API_KEY,
        @Query("busRouteId") busRouteId: String,
        @Query("resultType") resultType: String = "json",
    ): List<BusLocationResponse>
}