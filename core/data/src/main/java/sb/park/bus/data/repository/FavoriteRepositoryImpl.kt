package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.room.FavoriteDao
import sb.park.model.ApiResult
import sb.park.model.response.FavoriteEntity
import sb.park.model.safeFlow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(private val favoriteDao: FavoriteDao) :
    FavoriteRepository {
    override suspend fun insertFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.insertFavorite(favoriteEntity)
    }

    override fun getFavorite(): Flow<ApiResult<List<FavoriteEntity>>> = safeFlow {
        favoriteDao.getFavorite()
    }

    override suspend fun deleteFavorite(id: String) {
        favoriteDao.deleteFavorite(id)
    }

    override suspend fun deleteAll() {
        favoriteDao.deleteAll()
    }
}