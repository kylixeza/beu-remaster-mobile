package com.kylix.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.recipe.databinding.ItemStepBinding

class StepAdapter: BaseRecyclerViewAdapter<ItemStepBinding, String>() {

    override fun inflateViewBinding(parent: ViewGroup): ItemStepBinding {
        return ItemStepBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun ItemStepBinding.bindWithPosition(item: String, position: Int) {
        tvStep.text = item
        tvOrderedNumber.text = (position.plus(1)).toString().plus(".")
    }
}