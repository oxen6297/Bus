package sb.park.bus.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import sb.park.model.ApiResult
import sb.park.model.response.bus.GPSModel
import sb.park.model.safeFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@SuppressLint("MissingPermission")
class GPSRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : GPSRepository {

    private val locationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

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
        } ?: GPSModel(null, null)
    }

    override fun getGPS(): Flow<GPSModel> {
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5_000L).build()

        return callbackFlow {
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    p0.lastLocation?.let {
                        trySend(GPSModel(it.latitude, it.longitude))
                    }
                }
            }

            locationClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())

            awaitClose {
                locationClient.removeLocationUpdates(locationCallback)
            }
        }
    }
}