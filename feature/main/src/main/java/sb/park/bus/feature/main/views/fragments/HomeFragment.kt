package sb.park.bus.feature.main.views.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.common.base.BaseFragment
import sb.park.bus.feature.main.databinding.FragmentHomeBinding
import sb.park.bus.feature.main.viewmodels.BitCoinViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val mainViewModel: BitCoinViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            backPressed()
            vm = mainViewModel
            textSearch.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
            }
        }
    }

    private fun backPressed() {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finishAffinity()
            }
        }.apply {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, this)
        }
    }
}