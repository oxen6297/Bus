package sb.park.bus.data.repository

import sb.park.bus.data.model.CoinBaseModel

interface BitCoinRepository {
    suspend fun getData(): CoinBaseModel
}