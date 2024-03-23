package com.kylix.reset_password.ui

import androidx.lifecycle.viewModelScope
import com.kylix.reset_password.model.PasswordTerm
import com.kylix.reset_password.model.PasswordTermType
import com.kylix.common.base.BaseViewModel
import com.kylix.core.repositories.profile.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val profileRepository: ProfileRepository
): BaseViewModel() {

    private val _terms = MutableStateFlow(listOf(
        PasswordTerm(PasswordTermType.MINIMUM_8_CHARACTERS, "Minimal 8 karakter"),
        PasswordTerm(PasswordTermType.ONE_WORD_ONE_CHARACTER, "Minimal 1 huruf dan 1 angka"),
        PasswordTerm(PasswordTermType.ONE_SPECIAL_CHARACTER, "Minimal 1 karakter spesial"),
    ))
    val terms = _terms.asStateFlow()

    private val _isAllFullFilled = MutableStateFlow(false)
    val isAllFullFilled = _isAllFullFilled.asSharedFlow()

    fun onPasswordChanged(password: String) {
        _terms.value = _terms.value.map {
            val isFulfilled = when (it.type) {
                PasswordTermType.MINIMUM_8_CHARACTERS -> password.length >= 8
                PasswordTermType.ONE_WORD_ONE_CHARACTER -> password.matches("^(?=.*[A-Za-z])(?=.*\\d).+\$".toRegex())
                PasswordTermType.ONE_SPECIAL_CHARACTER -> password.matches("^(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+\$".toRegex())
            }
            it.copy(isFulfilled = isFulfilled)
        }
        _isAllFullFilled.value = _terms.value.all { it.isFulfilled }
    }

    fun resetPassword(password: String) {
        viewModelScope.launch {
            onDataLoading()
            profileRepository.resetPassword(password).fold(
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