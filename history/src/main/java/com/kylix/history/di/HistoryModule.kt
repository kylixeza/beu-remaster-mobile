package com.kylix.history.di

import com.kylix.history.ui.HistoryViewModel
import com.kylix.history.ui.review_preview.ReviewPreviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val historyModule = module {
    viewModel { HistoryViewModel(get()) }
    viewModel { ReviewPreviewViewModel(get()) }
}