package sb.park.bus.data.repository

import com.github.mikephil.charting.data.CandleEntry
import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult
import sb.park.model.response.bitcoin.BaseResponse

interface BitCoinRepository {
    fun getData(): Flow<ApiResult<BaseResponse>>

    suspend fun insertData()

    fun getChartData(): Flow<ApiResult<List<CandleEntry>>>
}