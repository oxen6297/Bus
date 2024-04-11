package sb.park.bus.feature.main.views.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.adapter.FavoriteAdapter
import sb.park.bus.feature.main.common.base.BaseFragment
import sb.park.bus.feature.main.databinding.FragmentHomeBinding
import sb.park.bus.feature.main.extensions.customDialog
import sb.park.bus.feature.main.extensions.showToast
import sb.park.bus.feature.main.extensions.singleClickListener
import sb.park.bus.feature.main.viewmodels.HomeViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backPressedCallback = object : OnBackPressedCallback(true) {
            private var clickTime = 0L
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - clickTime >= 1000L) {
                    clickTime = System.currentTimeMillis()
                    view.context.showToast(getString(R.string.toast_back))
                } else {
                    requireActivity().finishAffinity()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )

        binding.apply {
            vm = viewModel.apply {
                getFavorite()
            }

            adapter = FavoriteAdapter {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.toDelivery())
                )
            }

            textSearch.singleClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
            }

            textDeleteFavorite.singleClickListener {
                it.context.customDialog(getString(R.string.popup_delete_all)) {
                    viewModel.deleteAll()
                }
            }
        }
    }
}