package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.FavoriteRepository
import sb.park.model.response.bus.ArgumentData
import sb.park.model.response.bus.FavoriteEntity
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {

    fun getFavorite(): Flow<List<FavoriteEntity>> = favoriteRepository.getFavorite()

    fun isBusFavorite(argumentData: ArgumentData): Flow<Boolean> =
        favoriteRepository.isBusFavorite(argumentData)

    suspend fun addFavorite(argumentData: ArgumentData) =
        favoriteRepository.addFavorite(argumentData)

    suspend fun deleteAll(fetchEmptyList: suspend () -> Unit) {
        favoriteRepository.deleteAll()
        fetchEmptyList()
    }
}