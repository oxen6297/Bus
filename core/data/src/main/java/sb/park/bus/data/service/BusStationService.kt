package sb.park.bus.data.service

import retrofit2.http.GET
import retrofit2.http.Query
import sb.park.model.response.BusResponse
import sb.park.bus.data.util.API_KEY

interface BusStationService {
    @GET("busRouteInfo/getStaionByRoute")
    suspend fun getData(
        @Query("serviceKey") serviceKey: String = API_KEY,
        @Query("busRouteId") busRouteId: String,
        @Query("resultType") resultType: String = "json",
    ): BusResponse
}