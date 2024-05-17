package sb.park.bus.feature.main.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.extensions.customDialog

object PermissionUtil {

    fun checkPermission(context: Context): Boolean {
        context.apply {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val permissionList = arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )

            val isGranted = permissionList.all {
                ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
            }

            if (!isGranted) {
                customDialog(getString(R.string.dialog_location_setting)) {
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:${packageName}")
                    )
                    startActivity(intent)
                }
                return false
            }

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                customDialog(getString(R.string.dialog_gps)) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                return false
            }

            return true
        }
    }
}