package com.kylix.onboard.di

import com.kylix.onboard.ui.OnBoardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onBoardModule = module {
    viewModel { OnBoardViewModel() }
}