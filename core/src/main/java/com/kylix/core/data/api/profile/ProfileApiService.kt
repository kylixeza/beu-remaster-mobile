package com.kylix.core.data.api.profile

import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.core.data.api.model.user.PasswordRequest
import com.kylix.core.data.api.model.user.UserRequest
import com.kylix.core.data.api.model.user.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface ProfileApiService {

    @GET("profile/greet")
    suspend fun greet(): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>>

    @GET("profile")
    suspend fun getProfile(): NetworkResponse<BaseResponse<UserResponse>, BaseResponse<Unit>>

    @Multipart
    @PUT("profile")
    suspend fun updateProfile(
        @Part("body") body: UserRequest,
        @Part avatar: MultipartBody.Part?
    ): NetworkResponse<BaseResponse<UserResponse>, BaseResponse<Unit>>

    @PUT("profile/reset-password")
    suspend fun resetPassword(
        @Body body: PasswordRequest
    ): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>>
}