package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.DataStoreRepository
import javax.inject.Inject

class GetExpandedUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {

    operator fun invoke(): Flow<Boolean> = dataStoreRepository.isExpanded
}