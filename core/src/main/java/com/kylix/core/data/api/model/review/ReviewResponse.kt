package com.kylix.core.data.api.model.review

import com.kylix.common.model.Review

data class ReviewResponse(
    val reviewId: String,
    val username: String,
    val avatar: String,
    val rating: Int,
    val comment: String,
    val timeStamp: String,
    val images: List<String>
) {
    fun toReview() = Review(
        reviewId = reviewId,
        username = username,
        avatar = avatar,
        rating = rating,
        comment = comment,
        timeStamp = timeStamp,
        images = images
    )
}
