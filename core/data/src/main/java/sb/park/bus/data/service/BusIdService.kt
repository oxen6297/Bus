package sb.park.bus.data.service

import com.google.gson.JsonElement
import retrofit2.http.GET

interface BusIdService {
    @GET("oxen6297/Bus/master/Bus.json")
    suspend fun getBusId(): JsonElement
}