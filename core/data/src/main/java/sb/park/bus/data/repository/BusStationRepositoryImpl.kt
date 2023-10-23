package sb.park.bus.data.repository

import sb.park.bus.data.response.BusStationResponse
import sb.park.bus.data.service.BusStationService
import javax.inject.Inject

class BusStationRepositoryImpl @Inject constructor(private val busStationService: BusStationService) :
    BusStationRepository {
    override suspend fun getData(busId: String): List<BusStationResponse> =
        busStationService.getData(busRouteId = busId)
}