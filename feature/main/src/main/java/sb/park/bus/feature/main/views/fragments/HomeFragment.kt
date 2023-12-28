package sb.park.bus.feature.main.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sb.park.bus.data.ApiResult
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.common.base.BaseFragment
import sb.park.bus.feature.main.databinding.FragmentHomeBinding
import sb.park.bus.feature.main.extensions.hide
import sb.park.bus.feature.main.extensions.show
import sb.park.bus.feature.main.extensions.showToast
import sb.park.bus.feature.main.viewmodels.BitCoinViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val mainViewModel: BitCoinViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finishAffinity()
            }
        }.apply {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, this)
        }

        mainViewModel.getCoinData()
        fetchCoinData(view.context)

        binding.textSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun fetchCoinData(context: Context) {
        lifecycleScope.launch {
            mainViewModel.bitCoinFlow.collectLatest {
                when (it) {
                    is ApiResult.Loading -> binding.includeLoading.root.show()
                    is ApiResult.Success -> binding.apply {
                        includeLoading.root.hide()
                        coin = it.data.data
                    }

                    is ApiResult.Error -> {
                        binding.includeLoading.root.hide()
                        context.showToast(getString(R.string.toast_error))
                    }
                }
            }
        }
    }
}