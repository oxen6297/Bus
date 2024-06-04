package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.BusStationRepository
import sb.park.model.ApiResult
import sb.park.model.response.bus.NearStationResponse
import javax.inject.Inject

class NearStationListUseCase @Inject constructor(private val busStationRepository: BusStationRepository) {

    operator fun invoke(): Flow<ApiResult<List<NearStationResponse>>> =
        busStationRepository.getNearStationList()
}