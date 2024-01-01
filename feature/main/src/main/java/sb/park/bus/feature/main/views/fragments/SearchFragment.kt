package sb.park.bus.feature.main.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import sb.park.bus.data.onError
import sb.park.bus.data.onLoading
import sb.park.bus.data.onSuccess
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
            busViewModel.busIdFlow.collect { result ->
                result.apply {
                    onSuccess {
                        it.forEach { busIdResponse ->
                            busViewModel.getStationList(busIdResponse.routeId.toString())
                        }
                    }
                    onError {
                        binding.includeLoading.root.hide()
                        context.error(it.message!!)
                        context.showToast(getString(R.string.toast_error))
                    }
                    onLoading { binding.includeLoading.root.show() }
                }
            }
        }
    }

    private fun fetchStationData(context: Context) {
        lifecycleScope.launch {
            busViewModel.stationFlow.collect { result ->
                result.apply {
                    onSuccess {
                        searchAdapter.submitList(it)
                        binding.includeLoading.root.hide()
                    }
                    onError {
                        binding.includeLoading.root.hide()
                        context.error(it.message!!)
                        context.showToast(getString(R.string.toast_error))
                    }
                    onLoading { binding.includeLoading.root.show() }
                }
            }
        }
    }
}