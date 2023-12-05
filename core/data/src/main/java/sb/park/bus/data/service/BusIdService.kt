package sb.park.bus.data.service

import retrofit2.http.GET
import sb.park.bus.data.response.BusIdResponse

interface BusIdService {
    @GET("oxen6297/Bus/master/Bus.json")
    suspend fun getBusId(): List<BusIdResponse>
}