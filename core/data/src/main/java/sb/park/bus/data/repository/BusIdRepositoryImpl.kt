package sb.park.bus.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import sb.park.bus.data.response.BusIdResponse
import sb.park.bus.data.service.BusIdService
import javax.inject.Inject

internal class BusIdRepositoryImpl @Inject constructor(private val busIdService: BusIdService) :
    BusIdRepository {
    override suspend fun getData(busNumber: String): List<BusIdResponse> {
        return Gson().fromJson<List<BusIdResponse>>(
            busIdService.getBusId(),
            object : TypeToken<List<BusIdResponse>>() {}.type
        ).filter {
            it.routeName == busNumber
        }.distinctBy {
            it.routeId
        }
    }
}