package com.kylix.auth.ui.login

import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.core.repositories.auth.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    fun login(identifier: String, password: String) {
        viewModelScope.launch {
            onDataLoading()
            authRepository.login(identifier, password).let { result ->
                if (result.isRight()) {
                    onDataSuccess()
                } else {
                    onDataError(result.leftOrNull()?.message ?: "Unknown error")
                }
            }
        }
    }

}