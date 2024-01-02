package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.ApiResult
import sb.park.bus.data.response.BaseResponse

interface BitCoinRepository {
    fun getData(): Flow<ApiResult<BaseResponse>>
}