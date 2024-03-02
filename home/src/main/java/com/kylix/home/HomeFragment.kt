package com.kylix.home

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.kylix.common.R
import com.kylix.common.base.BaseFragment
import com.kylix.common.util.initLinearHorizontal
import com.kylix.common.util.initLinearVertical
import com.kylix.home.adapter.CategoryAdapter
import com.kylix.home.adapter.HomeAdapter
import com.kylix.home.adapter.RecipeAdapter
import com.kylix.home.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val viewModel by viewModel<HomeViewModel>()

    private val categoryAdapter by lazy { CategoryAdapter() }

    private val homeAdapter by lazy { HomeAdapter(
        onRecipeSelected = { recipeId ->

        }
    ) }

    override fun inflateViewBinding(container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentHomeBinding.bind() {
        rvCategory.initLinearHorizontal(requireContext(), categoryAdapter)
        rvHome.initLinearVertical(requireContext(), homeAdapter)
    }

    override fun onDataSuccessLoaded() {
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
    }

    override fun systemBarColor(): Int {
        return R.color.primary_700
    }

}