package com.kylix.auth.ui.register

import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.core.repositories.auth.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository
): BaseViewModel() {

    fun register(password: String, username: String, phone: String, name: String) {
        viewModelScope.launch {
            onDataLoading()
            authRepository.register(
                password = password,
                username = username,
                phoneNumber = phone,
                name = name
            ).let { result ->
                if (result.isRight()) {
                    onDataSuccess()
                } else {
                    onDataError(result.leftOrNull()?.message ?: "Unknown error")
                }
            }
        }
    }

}