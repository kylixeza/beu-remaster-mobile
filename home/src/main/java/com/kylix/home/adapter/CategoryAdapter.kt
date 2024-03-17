package com.kylix.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.common.model.Category
import com.kylix.home.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val onCategorySelected: (Category) -> Unit = {}
): BaseRecyclerViewAdapter<ItemCategoryBinding, Category>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemCategoryBinding {
        return ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun ItemCategoryBinding.bind(item: Category) {
        tvCategory.text = item.name
        root.setOnClickListener { onCategorySelected.invoke(item) }
    }
}