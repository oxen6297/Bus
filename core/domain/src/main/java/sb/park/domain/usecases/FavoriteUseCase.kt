package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.FavoriteRepository
import sb.park.model.ApiResult
import sb.park.model.response.bus.FavoriteEntity
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {

    suspend fun insertFavorite(favoriteEntity: FavoriteEntity) =
        favoriteRepository.insertFavorite(favoriteEntity)

    fun getFavorite(): Flow<ApiResult<List<FavoriteEntity>>> = favoriteRepository.getFavorite()

    suspend fun deleteFavorite(id: String) = favoriteRepository.deleteFavorite(id)

    suspend fun deleteAll() = favoriteRepository.deleteAll()
}