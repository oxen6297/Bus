package sb.park.bus.data.repository

import sb.park.bus.data.service.BitCoinService
import sb.park.bus.data.mapper.toData
import sb.park.bus.data.response.BaseResponse
import javax.inject.Inject

internal class BitCoinRepositoryImpl @Inject constructor(private val bitCoinService: BitCoinService) :
    BitCoinRepository {
    override suspend fun getData(): BaseResponse = bitCoinService.getData().toData()
}