package com.kylix.core.data.api.review

import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.core.data.api.model.review.ReviewRequest
import com.kylix.core.data.api.model.review.ReviewResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ReviewApiService {

    @Multipart
    @POST("histories/{historyId}/review")
    suspend fun postReview(
        @Path("historyId") historyId: String,
        @Part("body") body: ReviewRequest,
        @Part images: List<MultipartBody.Part>
    ): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>>

    @GET("histories/{historyId}/review")
    suspend fun getReview(
        @Path("historyId") historyId: String
    ): NetworkResponse<BaseResponse<ReviewResponse>, BaseResponse<Unit>>
}