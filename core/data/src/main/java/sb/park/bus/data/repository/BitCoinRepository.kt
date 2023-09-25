package sb.park.bus.data.repository

import sb.park.bus.data.model.BitCoinModel

internal interface BitCoinRepository {
    suspend fun getData(): BitCoinModel
}