package sb.park.bus.data.service

import retrofit2.http.GET
import sb.park.model.response.bitcoin.BaseResponse

interface BitCoinService {
    @GET("BTC_KRW")
    suspend fun getData(): BaseResponse
}