package com.kylix.help_center.di

import com.kylix.help_center.ui.HelpCenterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val helpCenterModule = module {
    viewModel { HelpCenterViewModel(get(), get()) }
}