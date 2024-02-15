package com.kylix.splash.ui

import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.core.repositories.AuthRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authRepository: AuthRepository
): BaseViewModel() {

    private val _destination = MutableStateFlow<SplashDestination?>(null)
    val destination = _destination.asStateFlow()

    init {
        viewModelScope.launch {
            val isPassOnBoard = async { authRepository.isPassOnBoard() }.await()
            val isLoggedIn = async { authRepository.isLoggedIn() }.await()

            if (isPassOnBoard) _destination.value = SplashDestination.ONBOARD
            else if (isLoggedIn) _destination.value = SplashDestination.HOME
            else _destination.value = SplashDestination.LOGIN
        }
    }
}

enum class SplashDestination {
    ONBOARD,
    LOGIN,
    HOME
}