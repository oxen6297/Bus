package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult
import sb.park.bus.data.repository.BusStationRepository
import sb.park.model.response.BusSearchResponse
import javax.inject.Inject

class BusSearchUseCase @Inject constructor(private val busStationRepository: BusStationRepository) {

    operator fun invoke(busId: String): Flow<ApiResult<List<BusSearchResponse>>> =
        busStationRepository.getSearch(busId)
}