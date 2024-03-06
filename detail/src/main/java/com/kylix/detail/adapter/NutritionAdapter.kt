package com.kylix.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.common.model.Nutrition
import com.kylix.recipe.databinding.ItemNutritionBinding

class NutritionAdapter: BaseRecyclerViewAdapter<ItemNutritionBinding, Nutrition>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemNutritionBinding {
        return ItemNutritionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun ItemNutritionBinding.bind(item: Nutrition) {
        tvNutrition.text = item.name
        tvNutritionAmount.text = item.amount
    }
}