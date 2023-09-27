package sb.park.bus.data

import retrofit2.http.GET
import sb.park.bus.data.model.BitCoinModel

interface BitApi {
    @GET("ALL_KRW")
    suspend fun getData(): BitCoinModel
}