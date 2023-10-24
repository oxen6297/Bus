package sb.park.bus.data.service

import retrofit2.http.GET
import sb.park.bus.data.response.BusIdResponse

interface BusIdService {
    @GET("/Bus/core/data/src/main/assets/Bus.json")
    suspend fun getBusId(): List<BusIdResponse>
}