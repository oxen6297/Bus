package sb.park.bus.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import sb.park.bus.data.AppDispatchers
import sb.park.bus.data.Dispatcher
import sb.park.bus.data.room.FavoriteDao
import sb.park.model.ApiResult
import sb.park.model.response.bus.FavoriteEntity
import sb.park.model.safeFlow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
    @Dispatcher(AppDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher
) : FavoriteRepository {
    override suspend fun insertFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.insertFavorite(favoriteEntity)
    }

    override fun getFavorite(): Flow<ApiResult<List<FavoriteEntity>>> = safeFlow {
        favoriteDao.getFavorite()
    }.flowOn(coroutineDispatcher)

    override suspend fun deleteFavorite(id: String) {
        favoriteDao.deleteFavorite(id)
    }

    override suspend fun deleteAll() {
        favoriteDao.deleteAll()
    }
}