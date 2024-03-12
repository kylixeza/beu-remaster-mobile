package com.kylix.review.ui

import android.content.Intent
import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.core.repositories.review.ReviewRepository
import com.kylix.review.ui.ReviewActivity.Companion.EXTRA_HISTORY_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val reviewRepository: ReviewRepository
): BaseViewModel() {

    private val _rating = MutableStateFlow(0)

    private val _comment = MutableStateFlow("")

    private val _photos = MutableStateFlow(emptyList<Bitmap>())
    val photos = _photos.asStateFlow()

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled = _isButtonEnabled.asStateFlow()

    fun setRating(rating: Int) {
        _rating.value = rating
        isButtonEnabled()
    }

    fun setComment(comment: String) {
        _comment.value = comment
        isButtonEnabled()
    }

    fun addPhoto(photo: Bitmap) {
        _photos.value = _photos.value + photo
    }

    fun removePhoto(photo: Bitmap) {
        _photos.value = _photos.value - photo
    }

    private fun isButtonEnabled() {
        _isButtonEnabled.value = _rating.value > 0 && _comment.value.isNotBlank()
    }

    fun submitReview(intent: Intent) {
        val historyId = intent.getStringExtra(EXTRA_HISTORY_ID).orEmpty()
        onDataLoading()
        viewModelScope.launch {
            reviewRepository.postReview(historyId, _rating.value, _comment.value, _photos.value).onLeft {
                onDataError(it.message)
            }.onRight {
                onDataSuccess()
            }
        }
    }

}