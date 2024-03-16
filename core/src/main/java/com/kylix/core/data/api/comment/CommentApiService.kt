package com.kylix.core.data.api.comment

import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.core.data.api.model.comment.CommentRequest
import com.kylix.core.data.api.model.comment.CommentResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentApiService {

    @POST("recipes/{recipeId}/comments")
    suspend fun postComment(
        @Path("recipeId") recipeId: String,
        @Body body: CommentRequest
    ): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>>


    @GET("recipes/{recipeId}/comments")
    suspend fun getComments(
        @Path("recipeId") recipeId: String
    ): NetworkResponse<BaseResponse<List<CommentResponse>>, BaseResponse<Unit>>

}