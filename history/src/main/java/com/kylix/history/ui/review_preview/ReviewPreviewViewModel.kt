package com.kylix.history.ui.review_preview

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.common.model.Review
import com.kylix.core.repositories.review.ReviewRepository
import com.kylix.history.ui.review_preview.ReviewPreviewFragment.Companion.EXTRA_HISTORY_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReviewPreviewViewModel(
    private val reviewRepository: ReviewRepository
) : BaseViewModel() {

    private val _review = MutableStateFlow<Review?>(null)
    val review = _review.asStateFlow()

    fun getReview(bundle: Bundle) {
        val historyId = bundle.getString(EXTRA_HISTORY_ID).orEmpty()
        onDataLoading()
        viewModelScope.launch {
            reviewRepository.getReview(historyId).fold(
                ifLeft = {
                    onDataError(it.message)
                },
                ifRight = {
                    onDataSuccess()
                    _review.value = it.data
                }
            )
        }
    }

}