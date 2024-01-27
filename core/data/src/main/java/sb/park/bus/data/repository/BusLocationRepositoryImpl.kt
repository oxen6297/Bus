package sb.park.bus.data.repository

import sb.park.bus.data.mapper.toData
import sb.park.model.response.BusLocationResponse
import sb.park.bus.data.service.BusLocationService
import javax.inject.Inject

internal class BusLocationRepositoryImpl @Inject constructor(private val busLocationService: BusLocationService) :
    BusLocationRepository {

    override suspend fun getData(busId: String): List<BusLocationResponse> =
        busLocationService.getData(busRouteId = busId).map {
            it.toData()
        }
}