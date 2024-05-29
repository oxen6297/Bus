package sb.park.bus.data.service

import retrofit2.http.GET
import retrofit2.http.Query
import sb.park.bus.data.util.API_KEY
import sb.park.bus.data.util.BUS_ARRIVE_SERVICE
import sb.park.bus.data.util.BUS_LOCATION_SERVICE
import sb.park.bus.data.util.BUS_NEAR_STATION_SERVICE
import sb.park.bus.data.util.BUS_STATION_INFO_SERVICE
import sb.park.bus.data.util.BUS_STATION_SERVICE
import sb.park.bus.data.util.RADIUS
import sb.park.bus.data.util.RESULT_TYPE
import sb.park.model.response.bus.BusResponse

internal interface BusService {
    @GET(BUS_STATION_SERVICE)
    suspend fun getStation(
        @Query("serviceKey") serviceKey: String = API_KEY,
        @Query("busRouteId") busRouteId: String,
        @Query("resultType") resultType: String = RESULT_TYPE,
    ): BusResponse

    @GET(BUS_STATION_INFO_SERVICE)
    suspend fun getInfo(
        @Query("arsId") arsId: String,
        @Query("serviceKey") serviceKey: String = API_KEY,
        @Query("resultType") resultType: String = RESULT_TYPE
    ): BusResponse

    @GET(BUS_ARRIVE_SERVICE)
    suspend fun getArrive(
        @Query("busRouteId") busRouteId: String,
        @Query("ord") seq: String,
        @Query("stId") stationId: String,
        @Query("serviceKey") serviceKey: String = API_KEY,
        @Query("resultType") resultType: String = RESULT_TYPE
    ): BusResponse

    @GET(BUS_LOCATION_SERVICE)
    suspend fun getLocation(
        @Query("busRouteId") busRouteId: String,
        @Query("serviceKey") serviceKey: String = API_KEY,
        @Query("resultType") resultType: String = RESULT_TYPE
    ): BusResponse

    @GET(BUS_NEAR_STATION_SERVICE)
    suspend fun getNearStation(
        @Query("tmX") tmX: String,
        @Query("tmY") tmY: String,
        @Query("radius") radius: String = RADIUS,
        @Query("serviceKey") serviceKey: String = API_KEY,
        @Query("resultType") resultType: String = RESULT_TYPE
    ): BusResponse
}