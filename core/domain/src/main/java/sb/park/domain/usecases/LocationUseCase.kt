package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.GPSRepository
import sb.park.model.ApiResult
import sb.park.model.response.bus.GPSModel
import javax.inject.Inject

class LocationUseCase @Inject constructor(private val gpsRepository: GPSRepository) {

    operator fun invoke(): Flow<ApiResult<GPSModel>> = gpsRepository.getLastLocation()
}