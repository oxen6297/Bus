package sb.park.bus.data

import retrofit2.http.GET
import sb.park.bus.data.model.CoinBaseModel

interface BitApi {
    @GET("BTC_KRW")
    suspend fun getData(): CoinBaseModel
}