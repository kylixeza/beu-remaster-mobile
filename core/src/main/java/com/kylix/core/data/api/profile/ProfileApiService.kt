package com.kylix.core.data.api.profile

import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.core.data.api.model.user.UserResponse
import retrofit2.http.GET

interface ProfileApiService {

    @GET("profile/greet")
    suspend fun greet(): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>>

    @GET("profile")
    suspend fun getProfile(): NetworkResponse<BaseResponse<UserResponse>, BaseResponse<Unit>>
}