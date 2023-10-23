package sb.park.bus.data.repository

import sb.park.bus.data.response.BaseResponse

interface BitCoinRepository {
    suspend fun getData(): BaseResponse
}