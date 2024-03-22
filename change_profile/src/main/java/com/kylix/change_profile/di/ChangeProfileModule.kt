package com.kylix.change_profile.di

import com.kylix.change_profile.ui.ChangeProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val changeProfileModule = module {
    viewModel { ChangeProfileViewModel(get()) }
}