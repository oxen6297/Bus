package sb.park.bus.feature.main.views.fragments

import android.annotation.SuppressLint
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
import sb.park.bus.feature.main.extensions.singleClickListener
import sb.park.bus.feature.main.utils.StationItemDecoration
import sb.park.bus.feature.main.viewmodels.DetailViewModel
import sb.park.model.response.bus.FavoriteEntity

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()

    @SuppressLint("SetTextI18n")
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
                if (viewModel.isFavorite.value!!) {
                    it.context.customDialog(getString(R.string.popup_delete)) {
                        viewModel.deleteFavorite()
                    }
                } else {
                    viewModel.addFavorite(FavoriteEntity.Type.BUS.type)
                }
            }

            btnLeft.apply {
                text = viewModel.bus.value?.startDirection + DIRECTION
                singleClickListener {
                    recyclerviewStation.smoothScrollToPosition(START_POSITION)
                }
            }

            btnRight.apply {
                text = viewModel.bus.value?.endDirection + DIRECTION
                singleClickListener {
                    recyclerviewStation.apply {
                        val layoutManager = layoutManager as LinearLayoutManager
                        val transferPosition = viewModel.getTransferPosition()

                        if (layoutManager.findFirstVisibleItemPosition() > transferPosition) {
                            smoothScrollToPosition(transferPosition - OFFSET_POSITION)
                        } else {
                            smoothScrollToPosition(transferPosition + OFFSET_POSITION)
                        }
                    }
                }
            }

            recyclerviewStation.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    when (newState) {
                        RecyclerView.SCROLL_STATE_IDLE -> {
                            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                            val focusPosition = layoutManager.findLastVisibleItemPosition()
                            val transferPosition = viewModel.getTransferPosition() + OFFSET_POSITION

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
        private const val OFFSET_POSITION = 5
        private const val DIRECTION = "방향"
    }
}