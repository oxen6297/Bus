package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.GPSRepository
import sb.park.model.response.bus.GPSModel
import javax.inject.Inject

class GPSUseCase @Inject constructor(private val gpsRepository: GPSRepository) {

    operator fun invoke(): Flow<GPSModel> = gpsRepository.getGPS()
}