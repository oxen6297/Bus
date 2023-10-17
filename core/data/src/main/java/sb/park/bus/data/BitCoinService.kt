package sb.park.bus.data

import retrofit2.http.GET
import sb.park.bus.data.model.BaseResponse

interface BitCoinService {
    @GET("BTC_KRW")
    suspend fun getData(): BaseResponse
}