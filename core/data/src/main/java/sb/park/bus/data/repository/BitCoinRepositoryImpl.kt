package sb.park.bus.data.repository

import sb.park.bus.data.BitApi
import sb.park.bus.data.mapper.toData
import sb.park.bus.data.model.CoinBaseModel
import javax.inject.Inject

internal class BitCoinRepositoryImpl @Inject constructor(private val bitApi: BitApi) :
    BitCoinRepository {
    override suspend fun getData(): CoinBaseModel = bitApi.getData().toData()
}