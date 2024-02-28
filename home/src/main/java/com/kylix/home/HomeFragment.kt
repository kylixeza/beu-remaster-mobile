package com.kylix.home

import android.view.ViewGroup
import com.kylix.common.R
import com.kylix.common.base.BaseFragment
import com.kylix.common.util.initLinearHorizontal
import com.kylix.common.util.initLinearVertical
import com.kylix.home.adapter.CategoryAdapter
import com.kylix.home.adapter.HomeAdapter
import com.kylix.home.adapter.RecipeAdapter
import com.kylix.home.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val categoryAdapter by lazy { CategoryAdapter() }

    private val recipeAdapter by lazy { RecipeAdapter(
        onRecipeSelected = { recipe ->

        }
    ) }

    private val homeAdapter by lazy { HomeAdapter(recipeAdapter) }

    override fun inflateViewBinding(container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentHomeBinding.bind() {
        rvCategory.initLinearHorizontal(requireContext(), categoryAdapter)
        rvHome.initLinearVertical(requireContext(), homeAdapter)
    }

    override fun systemBarColor(): Int {
        return R.color.primary_700
    }

}