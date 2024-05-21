package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.FavoriteRepository
import sb.park.model.response.bus.FavoriteEntity
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {

    suspend fun insertFavorite(favoriteEntity: FavoriteEntity, setFavorite: (Boolean) -> Unit) {
        favoriteRepository.insertFavorite(favoriteEntity)
        setFavorite.invoke(true)
    }

    fun getFavorite(): Flow<List<FavoriteEntity>> = favoriteRepository.getFavorite()

    suspend fun deleteBusFavorite(id: String, setFavorite: (Boolean) -> Unit) {
        favoriteRepository.deleteBusFavorite(id)
        setFavorite.invoke(false)
    }

    suspend fun deleteAll(fetchEmptyList: suspend () -> Unit) {
        favoriteRepository.deleteAll()
        fetchEmptyList()
    }
}