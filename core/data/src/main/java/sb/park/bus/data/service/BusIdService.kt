package sb.park.bus.data.service

import retrofit2.http.GET
import sb.park.bus.data.util.BUS_ID_SERVICE
import sb.park.model.response.bus.BusIdResponse

internal interface BusIdService {
    @GET(BUS_ID_SERVICE)
    suspend fun getBusId(): BusIdResponse
}