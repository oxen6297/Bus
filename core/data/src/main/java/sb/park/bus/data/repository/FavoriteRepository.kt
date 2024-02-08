package sb.park.bus.data.repository

import sb.park.model.response.FavoriteEntity

interface FavoriteRepository {
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    suspend fun getFavorite(): List<FavoriteEntity>

    suspend fun deleteFavorite(id: String)

    suspend fun deleteAll()
}