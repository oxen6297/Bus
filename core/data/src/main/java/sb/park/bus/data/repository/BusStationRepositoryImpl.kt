package sb.park.bus.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import sb.park.bus.data.response.BusStationResponse
import sb.park.bus.data.service.BusStationService
import javax.inject.Inject

class BusStationRepositoryImpl @Inject constructor(private val busStationService: BusStationService) :
    BusStationRepository {
    override suspend fun getData(busId: String): List<BusStationResponse> {
        return Gson().fromJson<List<BusStationResponse>?>(
            busStationService.getData(busRouteId = busId).msgBody.itemList,
            object : TypeToken<List<BusStationResponse>>() {}.type
        ).distinctBy {
            it.direction
        }
    }
}