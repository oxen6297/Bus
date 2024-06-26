package sb.park.bus.presentation.views.fragments

import android.content.Context
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sb.park.bus.presentation.R
import sb.park.bus.presentation.adapter.FavoriteAdapter
import sb.park.bus.presentation.common.base.BaseFragment
import sb.park.bus.presentation.databinding.FragmentHomeBinding
import sb.park.bus.presentation.extensions.customDialog
import sb.park.bus.presentation.extensions.showToast
import sb.park.bus.presentation.extensions.singleClickListener
import sb.park.bus.presentation.utils.PermissionUtil
import sb.park.bus.presentation.viewmodels.HomeViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private val viewModel: HomeViewModel by viewModels()
    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.toArgument())
            )
        }
    }

    override fun initView(view: View) {
        bind {
            vm = viewModel.apply { getFavorite() }
            adapter = favoriteAdapter

            textSearch.singleClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
            }

            textDeleteFavorite.singleClickListener {
                it.context.customDialog(getString(R.string.popup_delete_all)) {
                    viewModel.deleteAll()
                }
            }

            layoutMap.singleClickListener {
                if (PermissionUtil.checkPermission(it.context)) {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToNearStationMapFragment()
                    )
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            private var clickTime = 0L
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - clickTime >= 1000L) {
                    clickTime = System.currentTimeMillis()
                    context.showToast(getString(R.string.toast_back))
                } else {
                    requireActivity().finishAffinity()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onDetach() {
        super.onDetach()
        onBackPressedCallback.remove()
    }
}