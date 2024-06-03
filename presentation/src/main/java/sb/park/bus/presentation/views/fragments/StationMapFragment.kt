package sb.park.bus.presentation.views.fragments

import android.view.View
import androidx.annotation.UiThread
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import sb.park.bus.presentation.R
import sb.park.bus.presentation.adapter.StationInfoAdapter
import sb.park.bus.presentation.common.base.BaseFragment
import sb.park.bus.presentation.databinding.FragmentStationMapBinding
import sb.park.bus.presentation.extensions.setMarker
import sb.park.bus.presentation.extensions.setOnSlide
import sb.park.bus.presentation.utils.ItemDecoration
import sb.park.bus.presentation.viewmodels.StationMapViewModel

@AndroidEntryPoint
class StationMapFragment : BaseFragment<FragmentStationMapBinding>(R.layout.fragment_station_map),
    OnMapReadyCallback {

    private val viewModel: StationMapViewModel by viewModels()
    private val stationInfoAdapter: StationInfoAdapter by lazy { StationInfoAdapter() }
    private val itemDecoration: ItemDecoration by lazy { ItemDecoration() }

    override fun initView(view: View) {
        bind {
            vm = viewModel
            adapter = stationInfoAdapter
            decoration = itemDecoration
            BottomSheetBehavior.from(layoutBottomSheet).setOnSlide { _, value ->
                layoutTransparent.isVisible = value > 0.5
            }
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapview) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.mapview, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(p0: NaverMap) {
        viewModel.argData.observe(this@StationMapFragment) {
            val latLng = LatLng(it.gpsY.toDouble(), it.gpsX.toDouble())
            p0.apply {
                locationOverlay.isVisible = true
                locationSource = FusedLocationSource(this@StationMapFragment, REQUEST_CODE)
                moveCamera(CameraUpdate.scrollTo(latLng))
                binding.btnLocation.map = this
                setMarker(it.gpsY.toDouble(), it.gpsX.toDouble(), it.stationNm)
            }
        }
    }

    companion object {
        private const val REQUEST_CODE = 1000
    }
}