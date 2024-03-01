package com.kylix.auth.di

import com.kylix.auth.ui.login.LoginViewModel
import com.kylix.auth.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
}