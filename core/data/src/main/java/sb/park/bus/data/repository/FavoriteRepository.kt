package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult
import sb.park.model.response.FavoriteEntity

interface FavoriteRepository {
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    fun getFavorite(): Flow<ApiResult<List<FavoriteEntity>>>

    suspend fun deleteFavorite(id: String)

    suspend fun deleteAll()
}