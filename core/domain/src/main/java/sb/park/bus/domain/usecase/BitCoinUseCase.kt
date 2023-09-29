package sb.park.bus.domain.usecase

import sb.park.bus.data.model.CoinBaseModel
import sb.park.bus.data.repository.BitCoinRepository
import javax.inject.Inject

class BitCoinUseCase @Inject constructor(private val bitCoinRepository: BitCoinRepository) {

    suspend operator fun invoke(): CoinBaseModel {
        return bitCoinRepository.getData()
    }
}