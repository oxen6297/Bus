package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.model.response.bus.ArgumentData
import sb.park.model.response.bus.FavoriteEntity

interface FavoriteRepository {

    fun getFavorite(): Flow<List<FavoriteEntity>>

    fun isBusFavorite(argumentData: ArgumentData): Flow<Boolean>

    suspend fun addFavorite(argumentData: ArgumentData)

    suspend fun deleteAll()
}