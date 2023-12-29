package sb.park.bus.feature.main.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import sb.park.bus.data.onError
import sb.park.bus.data.onLoading
import sb.park.bus.data.onSuccess
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.common.base.BaseFragment
import sb.park.bus.feature.main.common.error
import sb.park.bus.feature.main.databinding.FragmentHomeBinding
import sb.park.bus.feature.main.extensions.hide
import sb.park.bus.feature.main.extensions.repeatOnStarted
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

        binding.textSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        mainViewModel.getCoinData()
        fetchCoinData(view.context)
    }

    private fun fetchCoinData(context: Context) {
        repeatOnStarted {
            mainViewModel.bitCoinFlow.collectLatest { result ->
                result.apply {
                    onSuccess {
                        binding.coin = it.data
                        binding.includeLoading.root.hide()
                    }
                    onError {
                        context.error(it.message!!)
                        context.showToast(it.message!!)
                        binding.includeLoading.root.hide()
                    }
                    onLoading { binding.includeLoading.root.show() }
                }
            }
        }
    }
}