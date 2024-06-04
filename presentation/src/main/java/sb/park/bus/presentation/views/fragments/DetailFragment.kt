package sb.park.bus.presentation.views.fragments

import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
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
import sb.park.bus.presentation.R
import sb.park.bus.presentation.adapter.StationAdapter
import sb.park.bus.presentation.common.base.BaseFragment
import sb.park.bus.presentation.common.error
import sb.park.bus.presentation.databinding.FragmentDetailBinding
import sb.park.bus.presentation.extensions.showToast
import sb.park.bus.presentation.extensions.singleClickListener
import sb.park.bus.presentation.utils.ItemDecoration
import sb.park.bus.presentation.utils.PermissionUtil
import sb.park.bus.presentation.viewmodels.DetailViewModel

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()
    private val itemDecoration: ItemDecoration by lazy { ItemDecoration() }
    private val stationAdapter: StationAdapter by lazy {
        StationAdapter {
            if (PermissionUtil.checkPermission(requireContext())) {
                findNavController().navigate(
                    DetailFragmentDirections.actionDetailFragmentToStationMapFragment(it)
                )
            }
        }
    }

    override fun initView(view: View) {
        bind {
            vm = viewModel
            adapter = stationAdapter
            decoration = itemDecoration
            updateLocation()

            btnFloating.singleClickListener {
                val transValue = if (btnRefresh.isVisible) 0f else -200f

                showAnimation(btnRefresh, transValue)
                showAnimation(btnLocation, transValue * 2)
            }

            btnLocation.singleClickListener {
                if (PermissionUtil.checkPermission(it.context)) {
                    viewModel.getNearStation()
                }
            }

            btnLeft.singleClickListener {
                setScroll(START_POSITION)
            }

            btnRight.singleClickListener {
                setScroll(viewModel.transferPosition() + offsetPosition())
            }

            recyclerviewStation.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    when (newState) {
                        RecyclerView.SCROLL_STATE_IDLE -> {
                            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                            val focusPosition = layoutManager.findLastVisibleItemPosition()
                            val transferPosition = viewModel.transferPosition() + offsetPosition()

                            btnLeft.isChecked = focusPosition < transferPosition
                            btnRight.isChecked = focusPosition >= transferPosition
                        }
                    }
                }
            })
        }
    }

    private fun updateLocation() {
        lifecycleScope.launch {
            viewModel.locationFlow.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            ).collectLatest { location ->
                error(location.distance.toString())

                if (location.distance > THREE_KILO) {
                    binding.root.context.showToast(getString(R.string.toast_over_distance))
                    return@collectLatest
                }

                stationAdapter.updateLocation(location) {
                    setScroll(it + offsetPosition())
                }
            }
        }
    }

    private fun showAnimation(button: FloatingActionButton, transValue: Float) {
        val alphaValue = if (button.isVisible) Pair(1f, 0f) else Pair(0f, 1f)

        ObjectAnimator.ofFloat(button, TRANSLATION_Y, transValue).apply {
            if (button.isVisible) doOnEnd { button.hide() }
            else doOnStart { button.show() }
        }.start()
        ObjectAnimator.ofFloat(button, ALPHA, alphaValue.first, alphaValue.second).start()
    }

    private fun setScroll(position: Int) {
        object : LinearSmoothScroller(binding.root.context) {
            override fun getVerticalSnapPreference(): Int = SNAP_TO_END
        }.run {
            targetPosition = position
            binding.recyclerviewStation.layoutManager?.startSmoothScroll(this)
        }
    }

    private fun offsetPosition(): Int {
        val layoutManager = binding.recyclerviewStation.layoutManager as LinearLayoutManager

        return layoutManager.run {
            (findLastVisibleItemPosition() - findFirstVisibleItemPosition() + 1) / 2
        }
    }

    companion object {
        private const val THREE_KILO = 3000
        private const val START_POSITION = 0
        private const val TRANSLATION_Y = "translationY"
        private const val ALPHA = "alpha"
    }
}