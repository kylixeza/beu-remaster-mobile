package com.kylix.camera.di

import com.kylix.camera.ui.result.PredictionResultViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cameraModule = module {
    viewModel { PredictionResultViewModel(get(), get()) }
}