package sb.park.bus.feature.main.views.fragments

import android.Manifest
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.adapter.StationAdapter
import sb.park.bus.feature.main.common.base.BaseFragment
import sb.park.bus.feature.main.common.error
import sb.park.bus.feature.main.databinding.FragmentDetailBinding
import sb.park.bus.feature.main.extensions.showToast
import sb.park.bus.feature.main.extensions.singleClickListener
import sb.park.bus.feature.main.utils.ItemDecoration
import sb.park.bus.feature.main.viewmodels.DetailViewModel

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()
    private val itemDecoration: ItemDecoration by lazy { ItemDecoration() }
    private val stationAdapter: StationAdapter by lazy {
        StationAdapter {
            findNavController().navigate(
                DetailFragmentDirections.actionDetailFragmentToStationMapFragment(it)
            )
        }
    }

    override fun initView(view: View) {
        bind {
            vm = viewModel.apply { setFavorite() }
            adapter = stationAdapter
            decoration = itemDecoration
            updateLocation(view.context)

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnFloating.singleClickListener {
                val translationMap = mapOf(true to 0f, false to -200f)
                val transValue = translationMap[btnRefresh.isVisible] ?: 0f

                showAnimation(btnRefresh, transValue)
                showAnimation(btnLocation, transValue * 2)
            }

            btnLocation.singleClickListener {
                if (checkPermission(it.context)) {
                    viewModel.getNearStation {
                        it.context.showToast(getString(R.string.toast_error_gps))
                    }
                }
            }

            btnFavorite.singleClickListener {
                when (viewModel.isFavorite.value) {
                    true -> viewModel.deleteFavorite { it.context.showToast(getString(R.string.toast_delete_favorite)) }
                    else -> viewModel.addFavorite { it.context.showToast(getString(R.string.toast_add_favorite)) }
                }
            }

            btnLeft.singleClickListener {
                recyclerviewStation.smoothScrollToPosition(START_POSITION)
            }

            btnRight.singleClickListener {
                object : LinearSmoothScroller(it.context) {
                    override fun getVerticalSnapPreference(): Int = SNAP_TO_END
                }.run {
                    targetPosition = viewModel.getTransferPosition()
                    recyclerviewStation.layoutManager?.startSmoothScroll(this)
                }
            }

            recyclerviewStation.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    when (newState) {
                        RecyclerView.SCROLL_STATE_IDLE -> {
                            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                            val focusPosition = layoutManager.findLastVisibleItemPosition()
                            val transferPosition = viewModel.getTransferPosition()

                            btnLeft.isChecked = focusPosition < transferPosition
                            btnRight.isChecked = focusPosition >= transferPosition
                        }
                    }
                }
            })
        }
    }

    private fun updateLocation(context: Context) {
        lifecycleScope.launch {
            viewModel.locationFlow.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            ).collectLatest { location ->
                error(location.distance.toString())

                if (location.distance > TWO_KILO) {
                    context.showToast(getString(R.string.toast_over_distance))
                    return@collectLatest
                }

                location.position?.let {
                    binding.recyclerviewStation.smoothScrollToPosition(it)
                    stationAdapter.updateLocation(it)
                }
            }
        }
    }

    private fun showAnimation(button: FloatingActionButton, transValue: Float) {
        val alphaMap = mapOf(true to Pair(1f, 0f), false to Pair(0f, 1f))
        val alphaValue = alphaMap[button.isVisible] ?: Pair(1f, 0f)

        ObjectAnimator.ofFloat(button, TRANSLATION_Y, transValue).apply {
            if (button.isVisible) doOnEnd { button.hide() }
            else doOnStart { button.show() }
        }.start()
        ObjectAnimator.ofFloat(button, ALPHA, alphaValue.first, alphaValue.second).start()
    }

    private fun checkPermission(context: Context): Boolean {
        val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
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
                context.showToast(getString(R.string.toast_location_setting))
                startActivity(this)
            }
            return false
        }

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            context.showToast(getString(R.string.toast_gps))
            return false
        }

        return true
    }

    companion object {
        private const val TWO_KILO = 2000
        private const val START_POSITION = 0
        private const val TRANSLATION_Y = "translationY"
        private const val ALPHA = "alpha"
    }
}