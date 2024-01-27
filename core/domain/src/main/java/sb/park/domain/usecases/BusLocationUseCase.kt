package sb.park.domain.usecases

import sb.park.bus.data.repository.BusLocationRepository
import sb.park.model.response.BusLocationResponse
import javax.inject.Inject

class BusLocationUseCase @Inject constructor(private val busLocationRepository: BusLocationRepository) {

    suspend operator fun invoke(busId: String): List<BusLocationResponse> =
        busLocationRepository.getData(busId)
}