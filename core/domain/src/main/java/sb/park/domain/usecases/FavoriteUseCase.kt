package sb.park.domain.usecases

import sb.park.bus.data.repository.FavoriteRepository
import sb.park.model.response.FavoriteEntity
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {

    suspend fun insertFavorite(favoriteEntity: FavoriteEntity) =
        favoriteRepository.insertFavorite(favoriteEntity)

    suspend fun getFavorite(): List<FavoriteEntity> = favoriteRepository.getFavorite()

    suspend fun deleteFavorite(id: String) = favoriteRepository.deleteFavorite(id)

    suspend fun deleteAll() = favoriteRepository.deleteAll()
}