package sb.park.bus.data.repository

import sb.park.bus.data.response.BusSearchResponse
import sb.park.bus.data.response.BusStationResponse

interface BusStationRepository {
    suspend fun getData(busId: String): List<BusStationResponse>

    suspend fun getSearch(busId: String): List<BusSearchResponse>
}