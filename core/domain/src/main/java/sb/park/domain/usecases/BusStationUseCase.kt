package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.BusStationRepository
import sb.park.model.ApiResult
import sb.park.model.response.bus.BusSearchResponse
import sb.park.model.response.bus.BusStationResponse
import javax.inject.Inject

class BusStationUseCase @Inject constructor(private val busStationRepository: BusStationRepository) {

    operator fun invoke(busSearchResponse: BusSearchResponse): Flow<ApiResult<List<BusStationResponse>>> =
        busStationRepository.getData(busSearchResponse)
}