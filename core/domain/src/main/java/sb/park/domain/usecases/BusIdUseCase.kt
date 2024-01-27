package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult
import sb.park.bus.data.repository.BusIdRepository
import sb.park.model.response.BusIdResponse
import javax.inject.Inject

class BusIdUseCase @Inject constructor(private val busIdRepository: BusIdRepository) {

    operator fun invoke(busNumber: String): Flow<ApiResult<List<BusIdResponse>>> =
        busIdRepository.getData(busNumber)
}