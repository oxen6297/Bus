package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult
import sb.park.bus.data.repository.BitCoinRepository
import sb.park.model.response.BaseResponse
import javax.inject.Inject

class BitCoinUseCase @Inject constructor(private val bitCoinRepository: BitCoinRepository) {

    operator fun invoke(): Flow<ApiResult<BaseResponse>> = bitCoinRepository.getData()
}