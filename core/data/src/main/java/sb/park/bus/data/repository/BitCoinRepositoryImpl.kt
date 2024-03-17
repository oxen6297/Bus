package sb.park.bus.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import sb.park.model.ApiResult
import sb.park.bus.data.AppDispatchers
import sb.park.bus.data.Dispatcher
import sb.park.bus.data.mapper.toData
import sb.park.model.response.bitcoin.BaseResponse
import sb.park.model.safeFlow
import sb.park.bus.data.service.BitCoinService
import javax.inject.Inject

internal class BitCoinRepositoryImpl @Inject constructor(
    private val bitCoinService: BitCoinService,
    @Dispatcher(AppDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher
) : BitCoinRepository {
    override fun getData(): Flow<ApiResult<BaseResponse>> = safeFlow {
        bitCoinService.getData().toData()
    }.flowOn(coroutineDispatcher)
}