package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult
import sb.park.model.response.BaseResponse

interface BitCoinRepository {
    fun getData(): Flow<ApiResult<BaseResponse>>
}