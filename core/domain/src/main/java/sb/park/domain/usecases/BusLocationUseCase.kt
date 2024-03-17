package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.BusLocationRepository
import sb.park.model.ApiResult
import sb.park.model.response.bus.BusLocationResponse
import javax.inject.Inject

class BusLocationUseCase @Inject constructor(private val busLocationRepository: BusLocationRepository) {

    operator fun invoke(busId: String): Flow<ApiResult<List<BusLocationResponse>>> =
        busLocationRepository.getData(busId)
}