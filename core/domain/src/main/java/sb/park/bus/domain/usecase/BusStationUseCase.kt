package sb.park.bus.domain.usecase

import sb.park.bus.data.repository.BusStationRepository
import sb.park.bus.data.response.BusStationResponse
import javax.inject.Inject

class BusStationUseCase @Inject constructor(private val busStationRepository: BusStationRepository) {

    suspend operator fun invoke(busId: String): List<BusStationResponse> {
        return busStationRepository.getData(busId)
    }
}