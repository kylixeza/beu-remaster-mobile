package com.kylix.category.di

import com.kylix.category.ui.CategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModule = module {
    viewModel { CategoryViewModel(get()) }
}