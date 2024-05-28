package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.BusStationRepository
import sb.park.model.ApiResult
import sb.park.model.response.bus.StationInfoResponse
import javax.inject.Inject

class StationInfoUseCase @Inject constructor(private val busStationRepository: BusStationRepository) {

    operator fun invoke(arsId: String): Flow<ApiResult<List<StationInfoResponse>>> =
        busStationRepository.getStationInfo(arsId)
}