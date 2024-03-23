package com.kylix.reset_password.di

import com.kylix.reset_password.ui.ResetPasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val resetPasswordModule = module {
    viewModel { ResetPasswordViewModel(get()) }
}