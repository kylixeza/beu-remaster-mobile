package com.kylix.auth.ui.register

import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.core.repositories.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository
): BaseViewModel() {

    fun register(email: String, password: String, username: String, phone: String, name: String) {
        viewModelScope.launch {
            onDataLoading()
            authRepository.register(
                email = email,
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