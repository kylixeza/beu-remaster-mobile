package com.kylix.update_profile.di

import com.kylix.update_profile.ui.UpdateProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val updateProfileModule = module {
    viewModel { UpdateProfileViewModel(get()) }
}