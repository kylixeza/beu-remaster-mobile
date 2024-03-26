package com.kylix.core.repositories.review

import android.graphics.Bitmap
import arrow.core.Either
import com.kylix.common.model.Review
import com.kylix.common.util.Error
import com.kylix.common.util.Success

interface ReviewRepository {
    suspend fun postReview(
        historyId: String,
        rating: Int,
        comment: String,
        images: List<Bitmap>
    ): Either<Error, Success<Unit>>

    suspend fun getReview(historyId: String): Either<Error, Success<Review>>
}