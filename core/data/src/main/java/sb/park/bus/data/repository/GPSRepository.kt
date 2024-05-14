package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult
import sb.park.model.response.bus.GPSModel

interface GPSRepository {

    fun getLastLocation(): Flow<ApiResult<GPSModel>>

    fun getGPS(): Flow<GPSModel>
}