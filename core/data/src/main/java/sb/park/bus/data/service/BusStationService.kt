package sb.park.bus.data.service

import retrofit2.http.GET
import retrofit2.http.Query
import sb.park.model.response.bus.BusResponse
import sb.park.bus.data.util.API_KEY
import sb.park.bus.data.util.BUS_STATION_SERVICE
import sb.park.bus.data.util.RESULT_TYPE

interface BusStationService {
    @GET(BUS_STATION_SERVICE)
    suspend fun getData(
        @Query("serviceKey") serviceKey: String = API_KEY,
        @Query("busRouteId") busRouteId: String,
        @Query("resultType") resultType: String = RESULT_TYPE,
    ): BusResponse
}