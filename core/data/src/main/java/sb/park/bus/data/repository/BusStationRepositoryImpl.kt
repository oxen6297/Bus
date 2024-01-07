package sb.park.bus.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import sb.park.bus.data.ApiResult
import sb.park.bus.data.AppDispatchers
import sb.park.bus.data.Dispatcher
import sb.park.bus.data.mapper.toData
import sb.park.bus.data.mapper.toSearch
import sb.park.bus.data.response.BusSearchResponse
import sb.park.bus.data.response.BusStationResponse
import sb.park.bus.data.safeFlow
import sb.park.bus.data.service.BusStationService
import javax.inject.Inject

class BusStationRepositoryImpl @Inject constructor(
    private val busStationService: BusStationService,
    @Dispatcher(AppDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher
) : BusStationRepository {
    override fun getData(busId: String): Flow<ApiResult<List<BusStationResponse>>> =
        safeFlow {
            Gson().fromJson<List<BusStationResponse>?>(
                busStationService.getData(busRouteId = busId).msgBody.itemList,
                object : TypeToken<List<BusStationResponse>>() {}.type
            ).distinctBy {
                it.direction
            }
        }.flowOn(coroutineDispatcher)

    override fun getSearch(busId: String): Flow<ApiResult<List<BusSearchResponse>>> =
        safeFlow {
            Gson().fromJson<List<BusStationResponse>?>(
                busStationService.getData(busRouteId = busId).msgBody.itemList,
                object : TypeToken<List<BusStationResponse>>() {}.type
            ).map {
                it.toData()
            }.distinctBy {
                it.direction
            }.toSearch()
        }.flowOn(coroutineDispatcher)
}