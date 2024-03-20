package sb.park.bus.data.repository

import sb.park.model.response.bus.FavoriteEntity

interface FavoriteRepository {
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    suspend fun getFavorite(): List<FavoriteEntity>

    suspend fun getStationFavorite(): List<FavoriteEntity>

    suspend fun deleteFavorite(id: String)

    suspend fun deleteAll()
}