package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.model.response.bus.FavoriteEntity

interface FavoriteRepository {
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    fun getFavorite(): Flow<List<FavoriteEntity>>

    suspend fun deleteBusFavorite(id: String)

    suspend fun deleteStationFavorite(stationId: String)

    suspend fun deleteAll()
}