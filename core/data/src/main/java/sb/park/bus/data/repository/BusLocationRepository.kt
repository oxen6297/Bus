package sb.park.bus.data.repository

import sb.park.bus.data.response.BusLocationResponse

interface BusLocationRepository {
    suspend fun getData(busId: String): List<BusLocationResponse>
}