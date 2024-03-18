package sb.park.bus.feature.main.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.adapter.StationAdapter
import sb.park.bus.feature.main.common.base.BaseFragment
import sb.park.bus.feature.main.databinding.FragmentDetailBinding
import sb.park.bus.feature.main.extensions.customDialog
import sb.park.bus.feature.main.extensions.showToast
import sb.park.bus.feature.main.extensions.singleClickListener
import sb.park.bus.feature.main.utils.StationItemDecoration
import sb.park.bus.feature.main.viewmodels.DetailViewModel

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = viewModel
            adapter = StationAdapter()
            recyclerviewStation.addItemDecoration(StationItemDecoration())

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnFavorite.singleClickListener {
                if (viewModel.isFavorite.value) {
                    view.context.customDialog(getString(R.string.popup_delete)) {
                        viewModel.deleteFavorite()
                    }
                } else {
                    viewModel.addFavorite {
                        view.context.showToast(getString(R.string.toast_delete))
                    }
                }
            }

            btnLeft.singleClickListener {
                recyclerviewStation.smoothScrollToPosition(0)
            }

            btnRight.singleClickListener {
                recyclerviewStation.smoothScrollToPosition(viewModel.getTransferPosition())
            }

            recyclerviewStation.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val focusPosition =
                            (recyclerviewStation.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        if (focusPosition < viewModel.getTransferPosition()) {
                            btnLeft.isChecked = true
                        } else {
                            btnRight.isChecked = true
                        }
                    }
                }
            })
        }
    }
}