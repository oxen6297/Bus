package sb.park.bus.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import sb.park.bus.data.AppDispatchers
import sb.park.bus.data.Dispatcher
import sb.park.model.ApiResult
import sb.park.model.response.bus.GPSModel
import sb.park.model.safeFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GPSRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
    @Dispatcher(AppDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher
) : GPSRepository {

    private val locationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    override fun getLastLocation(): Flow<ApiResult<GPSModel>> = safeFlow {
        val location = suspendCoroutine { continuation ->
            locationClient.lastLocation.addOnSuccessListener { location: Location? ->
                continuation.resume(location)
            }.addOnFailureListener { e ->
                e.printStackTrace()
                continuation.resume(null)
            }
        }

        location?.let {
            GPSModel(it.latitude, it.longitude)
        } ?: GPSModel()
    }.flowOn(coroutineDispatcher)
}