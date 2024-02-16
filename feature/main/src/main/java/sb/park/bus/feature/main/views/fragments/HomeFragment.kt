package sb.park.bus.feature.main.views.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.adapter.FavoriteAdapter
import sb.park.bus.feature.main.common.base.BaseFragment
import sb.park.bus.feature.main.databinding.FragmentHomeBinding
import sb.park.bus.feature.main.extensions.customDialog
import sb.park.bus.feature.main.viewmodels.HomeViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finishAffinity()
            }
        }.apply {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, this)
        }

        binding.apply {
            vm = viewModel
            adapter = FavoriteAdapter()
            recyclerviewFavorite.layoutManager = GridLayoutManager(view.context, 2)
            textSearch.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
            }
            textDeleteFavorite.setOnClickListener {
                view.context.customDialog("즐겨찾기 전체삭제를 하시겠습니까?") {
                    viewModel.deleteAll()
                }
            }
        }
    }
}