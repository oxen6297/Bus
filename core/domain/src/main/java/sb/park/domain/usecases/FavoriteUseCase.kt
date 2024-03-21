package sb.park.domain.usecases

import sb.park.bus.data.repository.FavoriteRepository
import sb.park.model.response.bus.FavoriteEntity
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {

    suspend fun insertFavorite(favoriteEntity: FavoriteEntity) =
        favoriteRepository.insertFavorite(favoriteEntity)

    suspend fun getFavorite(): List<FavoriteEntity> = favoriteRepository.getFavorite()

    suspend fun getStationFavorite(stationId: String): Boolean =
        favoriteRepository.getStationFavorite(stationId)

    suspend fun deleteFavorite(id: String) = favoriteRepository.deleteFavorite(id)

    suspend fun deleteStationFavorite(stationId: String) = favoriteRepository.deleteStationFavorite(stationId)

    suspend fun deleteAll() = favoriteRepository.deleteAll()
}