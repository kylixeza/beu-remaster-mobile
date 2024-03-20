package com.kylix.profile.ui

import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.common.model.User
import com.kylix.core.repositories.profile.ProfileRepository
import com.kylix.profile.R
import com.kylix.profile.model.ProfileSection
import com.kylix.profile.model.Setting
import com.kylix.profile.util.ProfileSetting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : BaseViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _profileSection = MutableStateFlow(emptyList<ProfileSection>())
    val profileSection = _profileSection.asStateFlow()

    init {
        _profileSection.value = listOf(
            ProfileSection(
                sectionName = "Account",
                settings = listOf(
                    Setting(
                        name = "Ubah Profil",
                        icon = R.drawable.ic_profile_change_profile,
                        setting = ProfileSetting.CHANGE_PROFILE
                    ),
                    Setting(
                        name = "Ubah Password",
                        icon = R.drawable.ic_profile_change_password,
                        setting = ProfileSetting.CHANGE_PASSWORD
                    ),
                    Setting(
                        name = "Riwayat",
                        icon = R.drawable.ic_profile_history,
                        setting = ProfileSetting.HISTORY
                    ),
                    Setting(
                        name = "Favorit",
                        icon = R.drawable.ic_profile_favorite,
                        setting = ProfileSetting.FAVORITE
                    ),
                )
            ),
            ProfileSection(
                sectionName = "Lainnya",
                settings = listOf(
                    Setting(
                        name = "Kebijakan Privasi",
                        icon = R.drawable.ic_profile_privacy_policy,
                        setting = ProfileSetting.PRIVACY_POLICY
                    ),
                    Setting(
                        name = "Syarat & Ketentuan",
                        icon = R.drawable.ic_profile_terms_and_conditions,
                        setting = ProfileSetting.TERMS_AND_CONDITIONS
                    ),
                    Setting(
                        name = "Bantuan",
                        icon = R.drawable.ic_profile_help,
                        setting = ProfileSetting.HELP
                    )
                )
            )
        )
    }

    fun getUser() {
        onDataLoading()
        viewModelScope.launch {
            profileRepository.getProfile().fold(
                ifLeft = {
                    onDataError(it.message)
                },
                ifRight = {
                    onDataSuccess()
                    _user.value = it.data
                }
            )
        }
    }

}