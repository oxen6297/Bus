package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.BusSearchRepository
import sb.park.model.ApiResult
import sb.park.model.response.bus.BusSearchResponse
import javax.inject.Inject

class BusSearchUseCase @Inject constructor(private val busSearchRepository: BusSearchRepository) {

    operator fun invoke(busNumber: String): Flow<ApiResult<List<BusSearchResponse>>> =
        busSearchRepository.getSearch(busNumber)
}