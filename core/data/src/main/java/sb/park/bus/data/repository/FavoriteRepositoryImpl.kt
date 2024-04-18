package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import sb.park.bus.data.room.FavoriteDao
import sb.park.model.response.bus.FavoriteEntity
import javax.inject.Inject

internal class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {
    override suspend fun insertFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.insertFavorite(favoriteEntity)
    }

    override fun getFavorite(): Flow<List<FavoriteEntity>> = flow {
        emit(favoriteDao.getFavorite())
    }

    override suspend fun deleteFavorite(id: String) {
        favoriteDao.deleteFavorite(id)
    }

    override suspend fun deleteStationFavorite(stationId: String) {
        favoriteDao.deleteStationFavorite(stationId)
    }

    override suspend fun deleteAll() {
        favoriteDao.deleteAll()
    }
}