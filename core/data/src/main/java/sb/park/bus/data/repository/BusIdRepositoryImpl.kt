package sb.park.bus.data.repository

import sb.park.bus.data.response.BusIdResponse
import sb.park.bus.data.service.BusIdService
import javax.inject.Inject

internal class BusIdRepositoryImpl @Inject constructor(private val busIdService: BusIdService) :
    BusIdRepository {
    override suspend fun getData(busNumber: String): List<BusIdResponse> = busIdService.getBusId()
}