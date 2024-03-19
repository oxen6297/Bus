package sb.park.bus.data.service

import retrofit2.http.GET
import sb.park.bus.data.util.BITCOIN_SERVICE
import sb.park.model.response.bitcoin.BaseResponse

interface BitCoinService {
    @GET(BITCOIN_SERVICE)
    suspend fun getData(): BaseResponse
}