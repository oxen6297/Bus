package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.DataStoreRepository
import javax.inject.Inject

class SaveExpandedUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {

    suspend operator fun invoke(isExpanded: Boolean) {
        dataStoreRepository.saveIsExpanded(isExpanded)
    }
}