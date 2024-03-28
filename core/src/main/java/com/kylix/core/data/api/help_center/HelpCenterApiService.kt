package com.kylix.core.data.api.help_center

import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.core.data.api.model.help_center.HelpCenterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface HelpCenterApiService {

    @POST("help-center")
    suspend fun postMessage(
        @Body body: HelpCenterRequest
    ): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>>
}