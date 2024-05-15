package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.BusStationRepository
import sb.park.model.ApiResult
import sb.park.model.response.bus.ArgumentData
import sb.park.model.response.bus.LocationModel
import javax.inject.Inject

class NearStationUseCase @Inject constructor(private val busStationRepository: BusStationRepository) {

    operator fun invoke(
        argumentData: ArgumentData,
        latitude: Double,
        longitude: Double
    ): Flow<ApiResult<LocationModel>> =
        busStationRepository.getNearStation(argumentData, latitude, longitude)
}