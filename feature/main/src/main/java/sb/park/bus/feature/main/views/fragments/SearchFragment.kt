package sb.park.bus.feature.main.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import sb.park.bus.data.ApiResult
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.adapter.SearchAdapter
import sb.park.bus.feature.main.common.base.BaseFragment
import sb.park.bus.feature.main.common.error
import sb.park.bus.feature.main.databinding.FragmentSearchBinding
import sb.park.bus.feature.main.extensions.hide
import sb.park.bus.feature.main.extensions.show
import sb.park.bus.feature.main.extensions.showToast
import sb.park.bus.feature.main.viewmodels.BusViewModel

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val busViewModel: BusViewModel by viewModels()
    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            recyclerviewBus.adapter = searchAdapter
            textSearch.doAfterTextChanged {
                fetchBusData(view.context)
                fetchStationData(view.context)
                busViewModel.getBusList(it.toString())
            }
        }
    }

    private fun fetchBusData(context: Context) {
        lifecycleScope.launch {
            busViewModel.bus.collect {
                when (it) {
                    is ApiResult.Loading -> binding.includeLoading.root.show()
                    is ApiResult.Success -> {
                        it.data.forEach { busIdResponse ->
                            busViewModel.getStationList(busIdResponse.routeId.toString())
                        }
                    }

                    is ApiResult.Error -> {
                        binding.includeLoading.root.hide()
                        context.error(it.e.toString())
                        context.showToast(getString(R.string.toast_error))
                    }
                }
            }
        }
    }

    private fun fetchStationData(context: Context) {
        lifecycleScope.launch {
            busViewModel.station.collect {
                when (it) {
                    is ApiResult.Loading -> binding.includeLoading.root.show()
                    is ApiResult.Success -> {
                        searchAdapter.submitList(it.data)
                        binding.includeLoading.root.hide()
                    }

                    is ApiResult.Error -> {
                        binding.includeLoading.root.hide()
                        context.error(it.e.toString())
                        context.showToast(getString(R.string.toast_error))
                    }
                }
            }
        }
    }
}