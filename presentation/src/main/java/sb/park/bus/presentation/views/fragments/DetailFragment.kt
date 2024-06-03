package sb.park.bus.presentation.views.fragments

import android.animation.ObjectAnimator
import android.content.Context
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
            vm = viewModel.apply { setFavorite() }
            adapter = stationAdapter
            decoration = itemDecoration
            updateLocation(view.context)

            btnFloating.singleClickListener {
                val translationMap = mapOf(true to 0f, false to -200f)
                val transValue = translationMap[btnRefresh.isVisible] ?: 0f

                showAnimation(btnRefresh, transValue)
                showAnimation(btnLocation, transValue * 2)
            }

            btnLocation.singleClickListener {
                if (PermissionUtil.checkPermission(it.context)) {
                    viewModel.getNearStation()
                }
            }

            btnFavorite.singleClickListener {
                when (viewModel.isFavorite.value) {
                    true -> viewModel.deleteFavorite { it.context.showToast(getString(R.string.toast_delete_favorite)) }
                    else -> viewModel.addFavorite { it.context.showToast(getString(R.string.toast_add_favorite)) }
                }
            }

            btnLeft.singleClickListener {
                setScroll(it.context, START_POSITION)
            }

            btnRight.singleClickListener {
                setScroll(it.context, viewModel.transferPosition() + offsetPosition())
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

    private fun updateLocation(context: Context) {
        lifecycleScope.launch {
            viewModel.locationFlow.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            ).collectLatest { location ->
                error(location.distance.toString())

                if (location.distance > THREE_KILO) {
                    context.showToast(getString(R.string.toast_over_distance))
                    return@collectLatest
                }

                stationAdapter.updateLocation(location) {
                    setScroll(context, it + offsetPosition())
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

    private fun setScroll(context: Context, position: Int) {
        object : LinearSmoothScroller(context) {
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