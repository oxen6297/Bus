package sb.park.bus.data.repository

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import sb.park.bus.data.AppDispatchers
import sb.park.bus.data.Dispatcher
import sb.park.bus.data.mapper.toData
import sb.park.bus.data.room.FavoriteDao
import sb.park.bus.data.service.BusService
import sb.park.bus.data.util.toList
import sb.park.model.ApiResult
import sb.park.model.response.bus.ArgumentData
import sb.park.model.response.bus.BusArriveResponse
import sb.park.model.response.bus.BusLocationResponse
import sb.park.model.response.bus.BusStationResponse
import sb.park.model.response.bus.LocationModel
import sb.park.model.response.bus.NearStationResponse
import sb.park.model.response.bus.StationInfoResponse
import sb.park.model.safeFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

internal class BusStationRepositoryImpl @Inject constructor(
    private val busService: BusService,
    private val favoriteDao: FavoriteDao,
    private val locationClient: FusedLocationProviderClient,
    @Dispatcher(AppDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher
) : BusStationRepository {

    /**
     * 버스 정류장 노선
     */
    override fun getStation(
        argumentData: ArgumentData
    ): Flow<ApiResult<List<BusStationResponse>>> = safeFlow {
        val locationList = busService.getLocation(
            busRouteId = argumentData.busId
        ).msgBody.itemList.toList<BusLocationResponse>().map {
            it.toData()
        }

        busService.getStation(
            busRouteId = argumentData.busId
        ).msgBody.itemList.toList<BusStationResponse>().map {
            val isFavorite = favoriteDao.isStationFavorite(it.stationId, argumentData.busId)

            it.toData(isFavorite, locationList) {
                CoroutineScope(coroutineDispatcher).launch {
                    favoriteDao.addStationFavorite(it, argumentData)
                }
            }
        }
    }.flowOn(coroutineDispatcher)


    /**
     * 정류장 정보
     */
    override fun getStationInfo(
        arsId: String
    ): Flow<ApiResult<List<StationInfoResponse>>> = safeFlow {
        busService.getInfo(arsId).msgBody.itemList.toList<StationInfoResponse>().map {
            it.toData()
        }
    }.flowOn(coroutineDispatcher)


    /**
     * 근처 정류장 리스트
     */
    override fun getNearStationList(): Flow<ApiResult<List<NearStationResponse>>> = safeFlow {
        val latitude = getMyLocation().latitude.toString()
        val longitude = getMyLocation().longitude.toString()
        busService.getNearStation(
            longitude,
            latitude
        ).msgBody.itemList.toList<NearStationResponse>()
    }

    /**
     * 내 근처 정류장 찾기
     */
    @SuppressLint("MissingPermission")
    override fun getNearStation(
        argumentData: ArgumentData
    ): Flow<ApiResult<LocationModel>> = safeFlow {
        val stationList = busService.getStation(
            busRouteId = argumentData.busId
        ).msgBody.itemList.toList<BusStationResponse>()

        val latitude = getMyLocation().latitude
        val longitude = getMyLocation().longitude

        LocationModel().apply {
            stationList.minBy { response ->
                val latDiff = Math.toRadians(response.gpsY.toDouble() - latitude)
                val lonDiff = Math.toRadians(response.gpsX.toDouble() - longitude)
                val calculateDiff = sin(latDiff / 2).pow(2.0) +
                        sin(lonDiff / 2).pow(2.0) *
                        cos(Math.toRadians(latitude)) *
                        cos(Math.toRadians(response.gpsY.toDouble()))
                val distance = (2 * asin(sqrt(calculateDiff)) * R).toInt()

                distance.also { this.distance = it }
            }.also {
                position = stationList.indexOf(it)
                arriveTime = getArriveTime(argumentData.busId, it.seq, it.stationId)
            }
        }
    }.flowOn(coroutineDispatcher)


    /**
     * 버스 도착 시간
     */
    override suspend fun getArriveTime(busId: String, seq: String, stationId: String): String =
        busService.getArrive(
            busId,
            seq,
            stationId
        ).msgBody.itemList.toList<BusArriveResponse>().first().arriveTime


    /**
     * 나의 좌표 가져 오기
     */
    @SuppressLint("MissingPermission")
    private suspend fun getMyLocation(): Location = suspendCoroutine { continuation ->
        locationClient.lastLocation.addOnSuccessListener {
            continuation.resume(it)
        }.addOnFailureListener { e ->
            e.printStackTrace()
        }
    }

    companion object {
        private const val R = 6372.8 * 1000
    }
}