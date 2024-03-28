package com.kylix.help_center.ui

import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.core.repositories.help_center.HelpCenterRepository
import com.kylix.core.repositories.profile.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HelpCenterViewModel(
    private val profileRepository: ProfileRepository,
    private val helpCenterRepository: HelpCenterRepository
): BaseViewModel() {

    private val _hasEmail = MutableStateFlow(false)
    val hasEmail = _hasEmail.asStateFlow()

    fun checkEmail() {
        viewModelScope.launch {
            onDataLoading()
            profileRepository.getProfile().fold(
                ifLeft = {
                    onDataError(it.message)
                },
                ifRight = {
                    _hasEmail.value = it.data.email != null
                }
            )
            onFinishLoading()
        }
    }

    fun postMessage(message: String) {
        viewModelScope.launch {
            onDataLoading()
            helpCenterRepository.postMessage(message).fold(
                ifLeft = {
                    onDataError(it.message)
                },
                ifRight = {
                    onDataSuccess()
                }
            )
        }
    }
}