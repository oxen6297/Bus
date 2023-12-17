package sb.park.bus.data.repository

import sb.park.bus.data.room.FavoriteEntity

interface FavoriteRepository {
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    suspend fun getFavorite(): List<FavoriteEntity>

    suspend fun deleteFavorite(id: Int)

    suspend fun deleteAll()
}