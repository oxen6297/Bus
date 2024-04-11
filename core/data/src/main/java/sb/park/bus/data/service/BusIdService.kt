package sb.park.bus.data.service

import com.google.gson.JsonElement
import retrofit2.http.GET
import sb.park.bus.data.util.BUS_ID_SERVICE

internal interface BusIdService {
    @GET(BUS_ID_SERVICE)
    suspend fun getBusId(): JsonElement
}