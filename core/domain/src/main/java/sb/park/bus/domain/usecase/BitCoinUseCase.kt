package sb.park.bus.domain.usecase

import sb.park.bus.data.response.BaseResponse
import sb.park.bus.data.repository.BitCoinRepository
import javax.inject.Inject

class BitCoinUseCase @Inject constructor(private val bitCoinRepository: BitCoinRepository) {

    suspend operator fun invoke(): BaseResponse {
        return bitCoinRepository.getData()
    }
}