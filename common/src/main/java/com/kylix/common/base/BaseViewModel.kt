package com.kylix.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel: ViewModel() {

    open val _uiState = MutableStateFlow<BaseUIState?>(null)
    val uiState = _uiState.asStateFlow()

    protected fun onDataLoading() {
        _uiState.value = BaseUIState(isLoading = true)
    }

    protected fun onFinishLoading() {
        _uiState.value = BaseUIState(isLoading = false)
    }

    protected fun onDataError(message: String) {
        _uiState.value = BaseUIState(isLoading = false, isError = true, errorMessage = message)
    }

    protected fun onDataSuccess() {
        _uiState.value = BaseUIState(isLoading = false, isSuccess = true)
    }

    protected fun onResetUIState() {
        _uiState.value = null
    }
}