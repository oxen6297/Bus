package sb.park.domain.usecases

import com.github.mikephil.charting.data.CandleEntry
import sb.park.bus.data.repository.BitCoinRepository
import sb.park.model.response.bitcoin.BitCoinEntity
import javax.inject.Inject

class GetChartUseCase @Inject constructor(private val bitCoinRepository: BitCoinRepository) {

    suspend operator fun invoke(): List<CandleEntry> = bitCoinRepository.getChartData()
}