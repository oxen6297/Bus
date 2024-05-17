package sb.park.bus.feature.main.views.fragments

import android.view.View
import androidx.annotation.UiThread
import androidx.navigation.fragment.navArgs
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import dagger.hilt.android.AndroidEntryPoint
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.common.base.BaseFragment
import sb.park.bus.feature.main.databinding.FragmentStationMapBinding

@AndroidEntryPoint
class StationMapFragment : BaseFragment<FragmentStationMapBinding>(R.layout.fragment_station_map),
    OnMapReadyCallback {

    override fun initView(view: View) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapview) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.mapview, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(p0: NaverMap) {
        val navArgs: StationMapFragmentArgs by navArgs()
        val latLng = LatLng(navArgs.gps.gpsY.toDouble(), navArgs.gps.gpsX.toDouble())

        Marker().apply {
            position = latLng
            captionText = navArgs.gps.stationNm
            captionOffset = CAPTION_OFFSET
            icon = MarkerIcons.BLUE
            map = p0.apply {
                locationOverlay.isVisible = true
                locationSource = FusedLocationSource(this@StationMapFragment, REQUEST_CODE)
                uiSettings.isLocationButtonEnabled = true
                moveCamera(CameraUpdate.scrollTo(latLng))
            }
        }
    }

    companion object {
        private const val CAPTION_OFFSET = 10
        private const val REQUEST_CODE = 1000
    }
}