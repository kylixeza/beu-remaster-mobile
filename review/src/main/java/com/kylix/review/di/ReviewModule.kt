package com.kylix.review.di

import com.kylix.review.ui.ReviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val reviewModule = module {
    viewModel { ReviewViewModel(get()) }
}