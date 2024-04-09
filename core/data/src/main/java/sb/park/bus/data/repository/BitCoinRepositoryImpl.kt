package sb.park.bus.data.repository

import com.github.mikephil.charting.data.CandleEntry
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import sb.park.bus.data.AppDispatchers
import sb.park.bus.data.Dispatcher
import sb.park.bus.data.mapper.toCandle
import sb.park.bus.data.mapper.toData
import sb.park.bus.data.mapper.toEntity
import sb.park.bus.data.room.BitCoinDao
import sb.park.bus.data.service.BitCoinService
import sb.park.model.ApiResult
import sb.park.model.response.bitcoin.BaseResponse
import sb.park.model.safeFlow
import javax.inject.Inject

internal class BitCoinRepositoryImpl @Inject constructor(
    private val bitCoinService: BitCoinService,
    private val bitCoinDao: BitCoinDao,
    @Dispatcher(AppDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher
) : BitCoinRepository {

    override fun getData(): Flow<ApiResult<BaseResponse>> = safeFlow {
        bitCoinService.getData().toData()
    }.flowOn(coroutineDispatcher)

    override suspend fun insertData() {
        runCatching {
            bitCoinService.getData()
        }.onSuccess {
            val bitCoinResponse = it.data.toEntity()
            val oneDay = 24 * 60 * 60 * 1000 //24시간
            val timeDifference = System.currentTimeMillis() - bitCoinResponse.date.toLong()

            if (timeDifference >= oneDay || getChartData().isEmpty()) {
                bitCoinDao.insertNewData(bitCoinResponse)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    override suspend fun getChartData(): List<CandleEntry> {
        val entries = mutableListOf<CandleEntry>()

        bitCoinDao.getData().forEachIndexed { index, bitCoinEntity ->
            entries.add(bitCoinEntity.toCandle(index))
        }

        return entries
    }
}