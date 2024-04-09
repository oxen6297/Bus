package sb.park.domain.usecases

import sb.park.bus.data.repository.BitCoinRepository
import javax.inject.Inject

class InsertBitCoinUseCase @Inject constructor(private val bitCoinRepository: BitCoinRepository) {

    suspend operator fun invoke() {
        bitCoinRepository.insertData()
    }
}