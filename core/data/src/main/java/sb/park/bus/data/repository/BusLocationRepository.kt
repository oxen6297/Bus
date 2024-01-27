package sb.park.bus.data.repository

import sb.park.model.response.BusLocationResponse

interface BusLocationRepository {
    suspend fun getData(busId: String): List<BusLocationResponse>
}