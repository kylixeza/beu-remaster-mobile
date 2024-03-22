package com.kylix.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.common.util.initLinearVertical
import com.kylix.profile.databinding.ItemSectionBinding
import com.kylix.profile.model.ProfileSection
import com.kylix.profile.model.Setting

class SectionAdapter(
    private val onItemSelected: (Setting) -> Unit
): BaseRecyclerViewAdapter<ItemSectionBinding, ProfileSection>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemSectionBinding {
        return ItemSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun ItemSectionBinding.bind(item: ProfileSection) {
        tvSectionName.text = item.sectionName

        val settingsAdapter = SettingsAdapter(
            onItemSelected = onItemSelected
        )
        rvSettings.initLinearVertical(root.context, settingsAdapter)
        settingsAdapter.submitList(item.settings)
    }
}