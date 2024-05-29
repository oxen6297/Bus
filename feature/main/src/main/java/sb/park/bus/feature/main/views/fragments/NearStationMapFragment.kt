package sb.park.bus.feature.main.views.fragments

import android.view.View
import androidx.annotation.UiThread
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.common.base.BaseFragment
import sb.park.bus.feature.main.databinding.FragmentNearStationMapBinding
import sb.park.bus.feature.main.extensions.showToast
import sb.park.bus.feature.main.viewmodels.NearStationMapViewModel

@AndroidEntryPoint
class NearStationMapFragment :
    BaseFragment<FragmentNearStationMapBinding>(R.layout.fragment_near_station_map),
    OnMapReadyCallback {

    private val viewModel: NearStationMapViewModel by viewModels()

    override fun initView(view: View) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapview) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.mapview, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(p0: NaverMap) {

        lifecycleScope.launch {
            viewModel.gpsFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collectLatest {
                val latLng = LatLng(it.latitude, it.longitude)

                Marker().apply {
                    position = latLng
                    captionText = getString(R.string.my_location)
                    captionOffset = CAPTION_OFFSET
                    icon = MarkerIcons.BLUE
                    map = p0.apply {
                        locationOverlay.isVisible = true
                        locationSource =
                            FusedLocationSource(this@NearStationMapFragment, REQUEST_CODE)
                        uiSettings.isLocationButtonEnabled = true
                        moveCamera(CameraUpdate.scrollTo(latLng))
                    }
                }
            }
        }
    }

    companion object {
        private const val CAPTION_OFFSET = 10
        private const val REQUEST_CODE = 1000
    }
}