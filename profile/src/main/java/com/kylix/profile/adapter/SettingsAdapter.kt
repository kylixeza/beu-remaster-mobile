package com.kylix.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.profile.databinding.ItemSettingBinding
import com.kylix.profile.model.Setting

class SettingsAdapter(
    private val onItemSelected: (Setting) -> Unit
): BaseRecyclerViewAdapter<ItemSettingBinding, Setting>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemSettingBinding {
        return ItemSettingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun ItemSettingBinding.bind(item: Setting) {
        ivIcon.setImageResource(item.icon)
        tvTitle.text = item.name
        root.setOnClickListener {
            onItemSelected(item)
        }
    }
}