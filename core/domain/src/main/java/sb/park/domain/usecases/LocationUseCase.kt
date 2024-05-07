package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.BusStationRepository
import sb.park.model.response.bus.ArgumentData
import javax.inject.Inject

class LocationUseCase @Inject constructor(private val busStationRepository: BusStationRepository) {

    operator fun invoke(
        argumentData: ArgumentData,
        latitude: Double,
        longitude: Double
    ): Flow<Int> = busStationRepository.getLocation(argumentData, latitude, longitude)
}