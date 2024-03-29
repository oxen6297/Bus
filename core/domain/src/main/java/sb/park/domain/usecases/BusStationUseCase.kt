package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.BusStationRepository
import sb.park.model.ApiResult
import sb.park.model.response.bus.BusSearchResponse
import sb.park.model.response.bus.BusStationResponse
import sb.park.model.response.bus.DeliveryData
import javax.inject.Inject

class BusStationUseCase @Inject constructor(private val busStationRepository: BusStationRepository) {

    operator fun invoke(deliveryData: DeliveryData): Flow<ApiResult<List<BusStationResponse>>> =
        busStationRepository.getData(deliveryData)
}