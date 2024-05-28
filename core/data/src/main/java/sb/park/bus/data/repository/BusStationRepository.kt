package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult
import sb.park.model.response.bus.ArgumentData
import sb.park.model.response.bus.BusStationResponse
import sb.park.model.response.bus.LocationModel
import sb.park.model.response.bus.StationInfoResponse

interface BusStationRepository {
    fun getData(argumentData: ArgumentData): Flow<ApiResult<List<BusStationResponse>>>

    fun getNearStation(
        argumentData: ArgumentData,
        latitude: Double,
        longitude: Double
    ): Flow<ApiResult<LocationModel>>

    suspend fun getArriveTime(busId: String, seq: String, stationId: String): String

    fun getStationInfo(arsId: String): Flow<ApiResult<List<StationInfoResponse>>>
}