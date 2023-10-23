package sb.park.bus.domain.usecase

import sb.park.bus.data.repository.BusLocationRepository
import sb.park.bus.data.response.BusLocationResponse
import javax.inject.Inject

class BusLocUseCase @Inject constructor(private val busLocationRepository: BusLocationRepository) {

    suspend operator fun invoke(busId: String): List<BusLocationResponse> {
        return busLocationRepository.getData(busId)
    }
}