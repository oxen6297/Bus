package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult
import sb.park.model.response.bus.ArgumentData
import sb.park.model.response.bus.BusStationResponse
import sb.park.model.response.bus.LocationModel
import sb.park.model.response.bus.NearStationResponse
import sb.park.model.response.bus.StationInfoResponse

interface BusStationRepository {
    fun getStation(argumentData: ArgumentData): Flow<ApiResult<List<BusStationResponse>>>

    fun getStationInfo(arsId: String): Flow<ApiResult<List<StationInfoResponse>>>

    fun getNearStationList(): Flow<ApiResult<List<NearStationResponse>>>

    fun getNearStation(
        argumentData: ArgumentData
    ): Flow<ApiResult<LocationModel>>

    suspend fun getArriveTime(busId: String, seq: String, stationId: String): String
}