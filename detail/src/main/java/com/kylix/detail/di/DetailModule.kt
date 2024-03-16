package com.kylix.detail.di

import com.kylix.comment.ui.CommentViewModel
import com.kylix.detail.ui.DetailRecipeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel { DetailRecipeViewModel(get(), get(), get()) }
    viewModel { CommentViewModel(get()) }
}