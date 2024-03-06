package com.kylix.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.recipe.databinding.ItemIngredientToolBinding

class IngredientToolAdapter: BaseRecyclerViewAdapter<ItemIngredientToolBinding, String>() {

    override fun inflateViewBinding(parent: ViewGroup): ItemIngredientToolBinding {
        return ItemIngredientToolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun ItemIngredientToolBinding.bind(item: String) {
        tvIngredientTool.text = item
    }
}