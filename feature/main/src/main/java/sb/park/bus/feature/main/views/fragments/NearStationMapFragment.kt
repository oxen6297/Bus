package sb.park.bus.feature.main.views.fragments

import android.content.Context
import android.view.View
import android.view.WindowManager
import androidx.annotation.UiThread
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.common.base.BaseFragment
import sb.park.bus.feature.main.databinding.FragmentNearStationMapBinding
import sb.park.bus.feature.main.extensions.hide
import sb.park.bus.feature.main.viewmodels.NearStationMapViewModel

@AndroidEntryPoint
class NearStationMapFragment :
    BaseFragment<FragmentNearStationMapBinding>(R.layout.fragment_near_station_map),
    OnMapReadyCallback {

    private val viewModel: NearStationMapViewModel by viewModels()

    override fun initView(view: View) {
        bind { vm = viewModel }

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapview) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.mapview, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(p0: NaverMap) {
        val naverMap = p0.apply {
            locationOverlay.isVisible = true
            locationSource = FusedLocationSource(this@NearStationMapFragment, REQUEST_CODE)
            locationTrackingMode = LocationTrackingMode.Follow
            uiSettings.isLocationButtonEnabled = true
            addOnLocationChangeListener {
                binding.layoutLoading.hide()
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        }

        lifecycleScope.launch {
            viewModel.stationFlow.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            ).collectLatest {
                it.forEach { response ->
                    Marker().apply {
                        position = LatLng(response.gpsY.toDouble(), response.gpsX.toDouble())
                        icon = OverlayImage.fromResource(R.drawable.marker_station)
                        width = 75
                        height = 75
                        captionText = response.stationNm
                        captionOffset = 10
                        captionRequestedWidth = 120
                        map = naverMap
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    companion object {
        private const val REQUEST_CODE = 1000
    }
}