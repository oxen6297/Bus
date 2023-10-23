package sb.park.bus.data.repository

import sb.park.bus.data.response.BusStationResponse

interface BusStationRepository {
    suspend fun getData(busId: String): List<BusStationResponse>
}