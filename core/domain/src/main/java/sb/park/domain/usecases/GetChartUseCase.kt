package sb.park.domain.usecases

import com.github.mikephil.charting.data.CandleEntry
import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.BitCoinRepository
import sb.park.model.ApiResult
import sb.park.model.response.bitcoin.BitCoinEntity
import javax.inject.Inject

class GetChartUseCase @Inject constructor(private val bitCoinRepository: BitCoinRepository) {

    operator fun invoke(): Flow<ApiResult<List<CandleEntry>>> = bitCoinRepository.getChartData()
}