package com.kylix.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.common.model.HomeRecipe
import com.kylix.common.util.dispose
import com.kylix.common.util.initLinearHorizontal
import com.kylix.home.databinding.ItemHomeBinding

class HomeAdapter(
    private val onRecipeSelected: (String) -> Unit = {}
): BaseRecyclerViewAdapter<ItemHomeBinding, HomeRecipe>() {

    override fun inflateViewBinding(parent: ViewGroup): ItemHomeBinding {
        return ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun ItemHomeBinding.bindWithPosition(item: HomeRecipe, position: Int) {
        tvTitle.text = item.title
        tvSubtitle.text = item.subtitle

        if (item.subtitle == null) tvSubtitle.dispose()

        val recipeAdapter = RecipeAdapter(position == itemList.size - 1, onRecipeSelected)

        rvRecipes.initLinearHorizontal(root.context, recipeAdapter)
        recipeAdapter.submitList(item.recipes)
    }
}