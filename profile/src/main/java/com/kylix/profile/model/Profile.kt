package com.kylix.profile.model

import com.kylix.profile.util.ProfileSetting

data class ProfileSection(
    val sectionName: String,
    val settings: List<Setting>
)

data class Setting(
    val name: String,
    val icon: Int,
    val setting: ProfileSetting,
)
