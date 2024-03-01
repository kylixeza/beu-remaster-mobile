package com.kylix.core.data.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.core.data.api.model.auth.LoginRequest
import com.kylix.core.data.api.model.auth.RegisterRequest
import com.kylix.core.data.api.model.auth.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("register")
    suspend fun register(
        @Body body: RegisterRequest
    ): NetworkResponse<BaseResponse<TokenResponse>, BaseResponse<Unit>>

    @POST("login")
    suspend fun login(
        @Body body: LoginRequest
    ): NetworkResponse<BaseResponse<TokenResponse>, BaseResponse<Unit>>

}