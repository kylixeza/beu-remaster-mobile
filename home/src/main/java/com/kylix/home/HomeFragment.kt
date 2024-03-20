package com.kylix.home

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.kylix.common.R
import com.kylix.common.base.BaseFragment
import com.kylix.common.util.initLinearHorizontal
import com.kylix.common.util.initLinearVertical
import com.kylix.home.adapter.CategoryAdapter
import com.kylix.home.adapter.HomeAdapter
import com.kylix.home.databinding.FragmentHomeBinding
import com.kylix.home.navigation.HomeNavigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val viewModel by viewModel<HomeViewModel>()

    private val homeNavigation by inject<HomeNavigation>()
    private val categoryAdapter by lazy { CategoryAdapter(
        onCategorySelected = { homeNavigation.navigateToCategory(requireActivity(), it.categoryId, it.name) }
    ) }

    private val homeAdapter by lazy { HomeAdapter(
        onRecipeSelected = { recipeId ->
            homeNavigation.navigateToDetail(recipeId, requireActivity())
        }
    ) }

    override fun inflateViewBinding(container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentHomeBinding.bind() {
        viewModel.getHomeData()

        rvCategory.initLinearHorizontal(requireContext(), categoryAdapter)
        rvHome.initLinearVertical(requireContext(), homeAdapter)

        appBarHome.ivSearch.setOnClickListener {
            homeNavigation.navigateToSearch(requireActivity())
        }
    }

    override fun FragmentHomeBinding.onDataSuccessLoaded() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.categories.collect {
                categoryAdapter.submitList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.homeRecipes.collect {
                homeAdapter.submitList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.greeting.collect {
                binding?.appBarHome?.tvGreeting?.text = it
            }
        }
    }

    override fun systemBarColor(): Int {
        return R.color.primary_700
    }

}