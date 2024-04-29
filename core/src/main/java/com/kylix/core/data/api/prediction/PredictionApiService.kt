package com.kylix.core.data.api.prediction

import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.core.data.api.model.prediction.PredictionResultRequest
import com.kylix.core.data.api.model.recipe.RecipeListResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface PredictionApiService {

    @Multipart
    @POST("prediction")
    suspend fun postPredictionResult(
        @Part("body") body: PredictionResultRequest,
        @Part image: MultipartBody.Part?
    ): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>>

    @GET("prediction")
    suspend fun getRelatedRecipes(
        @Query("query") query: String
    ): NetworkResponse<BaseResponse<List<RecipeListResponse>>, BaseResponse<Unit>>

}