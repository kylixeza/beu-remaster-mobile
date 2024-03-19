package com.kylix.core.data.api.profile

import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import retrofit2.http.GET

interface ProfileApiService {

    @GET("profile/greet")
    suspend fun greet(): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>>

}