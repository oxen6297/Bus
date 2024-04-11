package sb.park.bus.feature.main.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.adapter.SearchAdapter
import sb.park.bus.feature.main.common.base.BaseFragment
import sb.park.bus.feature.main.databinding.FragmentSearchBinding
import sb.park.bus.feature.main.viewmodels.SearchViewModel

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = searchViewModel
            adapter = SearchAdapter {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToDetailFragment(it.toDelivery())
                )
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}