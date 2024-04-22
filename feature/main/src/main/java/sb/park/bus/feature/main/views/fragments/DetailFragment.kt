package sb.park.bus.feature.main.views.fragments

import android.animation.ObjectAnimator
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.adapter.StationAdapter
import sb.park.bus.feature.main.common.base.BaseFragment
import sb.park.bus.feature.main.databinding.FragmentDetailBinding
import sb.park.bus.feature.main.extensions.customDialog
import sb.park.bus.feature.main.extensions.showToast
import sb.park.bus.feature.main.extensions.singleClickListener
import sb.park.bus.feature.main.utils.ItemDecoration
import sb.park.bus.feature.main.viewmodels.DetailViewModel

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()

    override fun initView(view: View) {
        bind {
            vm = viewModel.apply { setFavorite() }
            adapter = StationAdapter()
            recyclerviewStation.addItemDecoration(ItemDecoration())

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnFloating.singleClickListener {
                val translationMap = mapOf(true to GONE_VALUE, false to SHOW_VALUE)
                val transValue = translationMap[btnRefresh.isVisible] ?: GONE_VALUE

                ObjectAnimator.ofFloat(btnRefresh, TRANSLATION_Y, transValue).start()
                ObjectAnimator.ofFloat(btnLocation, TRANSLATION_Y, transValue * 2).start()

                btnRefresh.isVisible = !btnRefresh.isVisible
                btnLocation.isVisible = !btnLocation.isVisible
            }

            btnFavorite.singleClickListener {
                if (viewModel.isFavorite.value!!) {
                    it.context.customDialog(getString(R.string.popup_delete)) {
                        viewModel.deleteFavorite()
                    }
                } else {
                    viewModel.addFavorite {
                        it.context.showToast(getString(R.string.toast_add_favorite))
                    }
                }
            }

            btnLeft.singleClickListener {
                recyclerviewStation.smoothScrollToPosition(START_POSITION)
            }

            btnRight.singleClickListener {
                val smoothScroller = object : LinearSmoothScroller(it.context) {
                    override fun getVerticalSnapPreference(): Int = SNAP_TO_END
                }.apply {
                    targetPosition = viewModel.getTransferPosition()
                }

                recyclerviewStation.layoutManager?.startSmoothScroll(smoothScroller)
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

    companion object {
        private const val START_POSITION = 0
        private const val TRANSLATION_Y = "translationY"
        private const val GONE_VALUE = 0f
        private const val SHOW_VALUE = -200f
    }
}