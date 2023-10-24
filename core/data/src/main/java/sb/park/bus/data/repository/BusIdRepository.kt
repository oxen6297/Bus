package sb.park.bus.data.repository

import sb.park.bus.data.response.BusIdResponse

interface BusIdRepository {
    suspend fun getData(busNumber: String): List<BusIdResponse>
}