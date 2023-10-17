package sb.park.bus.data.repository

import sb.park.bus.data.model.BaseResponse

interface BitCoinRepository {
    suspend fun getData(): BaseResponse
}