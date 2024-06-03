package sb.park.bus.presentation.views.fragments

import android.content.Context
import android.view.View
import android.view.WindowManager
import androidx.annotation.UiThread
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sb.park.bus.presentation.R
import sb.park.bus.presentation.common.base.BaseFragment
import sb.park.bus.presentation.databinding.FragmentNearStationMapBinding
import sb.park.bus.presentation.extensions.hide
import sb.park.bus.presentation.extensions.setMarker
import sb.park.bus.presentation.viewmodels.NearStationMapViewModel

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
            ).collectLatest { nearStationList ->
                nearStationList.forEach {
                    naverMap.setMarker(it.gpsY.toDouble(), it.gpsX.toDouble(), it.stationNm)
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