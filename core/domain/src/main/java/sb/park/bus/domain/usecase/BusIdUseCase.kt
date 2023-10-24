package sb.park.bus.domain.usecase

import sb.park.bus.data.repository.BusIdRepository
import sb.park.bus.data.response.BusIdResponse
import javax.inject.Inject

class BusIdUseCase @Inject constructor(private val busIdRepository: BusIdRepository) {

    suspend operator fun invoke(busNumber: String): List<BusIdResponse> {
        return busIdRepository.getData(busNumber)
    }
}