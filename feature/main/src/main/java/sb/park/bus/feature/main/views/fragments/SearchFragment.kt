package sb.park.bus.feature.main.views.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.adapter.SearchAdapter
import sb.park.bus.feature.main.common.base.BaseFragment
import sb.park.bus.feature.main.databinding.FragmentSearchBinding
import sb.park.bus.feature.main.utils.ItemDecoration
import sb.park.bus.feature.main.viewmodels.SearchViewModel

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val searchViewModel: SearchViewModel by viewModels()
    private val itemDecoration: ItemDecoration by lazy { ItemDecoration() }
    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToDetailFragment(it.toArgument())
            )
        }
    }

    override fun initView(view: View) {
        bind {
            vm = searchViewModel
            adapter = searchAdapter
            decoration = itemDecoration
        }
    }
}