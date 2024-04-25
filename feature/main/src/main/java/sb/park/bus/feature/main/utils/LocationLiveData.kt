package sb.park.bus.feature.main.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationServices
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.extensions.showToast
import sb.park.model.response.location.LocationModel

class LocationLiveData(private val context: Context) : LiveData<LocationModel>() {

    override fun onActive() {
        super.onActive()

        if (!checkPermission()) return

        val locationClient = LocationServices.getFusedLocationProviderClient(context)

        locationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let { loc ->
                value = LocationModel(loc.latitude, loc.longitude)
            } ?: context.showToast(context.getString(R.string.toast_error_gps))
        }.addOnFailureListener { e ->
            context.showToast(context.getString(R.string.toast_error))
            e.printStackTrace()
        }
    }

    private fun checkPermission(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val permissionList = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        val isGranted = permissionList.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        if (!isGranted) {
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:${context.packageName}")
            ).apply {
                context.showToast(context.getString(R.string.toast_location_setting))
                context.startActivity(this)
            }
            return false
        }

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            context.showToast(context.getString(R.string.toast_gps))
            return false
        }

        return true
    }
}