package com.kylix.core.repositories.review

import android.graphics.Bitmap
import arrow.core.Either
import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.common.base.NetworkOnlyResource
import com.kylix.common.util.Error
import com.kylix.common.util.Success
import com.kylix.core.data.api.model.review.ReviewRequest
import com.kylix.core.data.api.review.ReviewApiService
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class ReviewRepositoryImpl(
    private val reviewApiService: ReviewApiService
): ReviewRepository {
    override suspend fun postReview(
        historyId: String,
        rating: Int,
        comment: String,
        images: List<Bitmap>
    ): Either<Error, Success<Unit>> {
       return object : NetworkOnlyResource<Unit, String>() {
           override suspend fun createCall(): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>> {
                val imageParts = images.map { it.toRequestBody() }
                val reviewRequest = ReviewRequest(rating, comment)
                return reviewApiService.postReview(historyId, reviewRequest, imageParts)
           }

           override fun String.mapTransform() {
               return
           }

       }.run()
    }

    private fun Bitmap.toRequestBody(): MultipartBody.Part {
        val byteArrayOutputStream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val requestBody = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", "image.jpg", requestBody)
    }

}