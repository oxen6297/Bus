package sb.park.bus.data.repository

import sb.park.bus.data.room.FavoriteDao
import sb.park.bus.data.room.FavoriteEntity
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(private val favoriteDao: FavoriteDao) :
    FavoriteRepository {
    override suspend fun insertFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.insertFavorite(favoriteEntity)
    }

    override suspend fun getFavorite(): List<FavoriteEntity> {
        return favoriteDao.getFavorite()
    }

    override suspend fun deleteFavorite(id: Int) {
        favoriteDao.deleteFavorite(id)
    }

    override suspend fun deleteAll() {
        favoriteDao.deleteAll()
    }
}