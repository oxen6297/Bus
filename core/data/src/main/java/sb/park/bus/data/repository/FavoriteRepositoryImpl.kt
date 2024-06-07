package sb.park.bus.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import sb.park.bus.data.AppDispatchers
import sb.park.bus.data.Dispatcher
import sb.park.bus.data.room.FavoriteDao
import sb.park.model.response.bus.ArgumentData
import sb.park.model.response.bus.FavoriteEntity
import javax.inject.Inject

internal class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
    @Dispatcher(AppDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher
) : FavoriteRepository {

    override fun getFavorite(): Flow<List<FavoriteEntity>> = flow {
        emit(favoriteDao.getFavorite())
    }.flowOn(coroutineDispatcher)

    override fun isBusFavorite(argumentData: ArgumentData): Flow<Boolean> = flow {
        emit(favoriteDao.isBusFavorite(argumentData.busId))
    }.flowOn(coroutineDispatcher)

    override suspend fun addFavorite(argumentData: ArgumentData) =
        favoriteDao.addBusFavorite(argumentData)

    override suspend fun deleteAll() = favoriteDao.deleteAll()
}