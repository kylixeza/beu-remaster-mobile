package com.kylix.update_profile.ui

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.common.model.User
import com.kylix.core.repositories.profile.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpdateProfileViewModel(
    private val repository: ProfileRepository
): BaseViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _uri = MutableStateFlow<Uri?>(null)
    val uri = _uri.asStateFlow()

    init {
        getUser()
    }

    private fun getUser() {
        onDataLoading()
        viewModelScope.launch {
            repository.getProfile().fold(
                ifLeft = {
                     onDataError(it.message)
                },
                ifRight = {
                    onFinishLoading()
                    _user.value = it.data
                }
            )
        }
    }

    fun updateUser(
        context: Context,
        username: String,
        name: String,
        email: String,
        phoneNumber: String
    ) {
        onDataLoading()
        viewModelScope.launch {
            repository.updateProfile(
                context = context,
                username = username,
                name = name,
                email = email,
                phoneNumber = phoneNumber,
                avatar = _uri.value
            ).fold(
                ifLeft = {
                    onDataError(it.message)
                },
                ifRight = {
                    onDataSuccess()
                }
            )
        }
    }

    fun setUri(uri: Uri?) {
        _uri.value = uri
    }

}