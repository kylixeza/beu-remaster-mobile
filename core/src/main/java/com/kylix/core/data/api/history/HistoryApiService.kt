package com.kylix.core.data.api.history

import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.core.data.api.model.history.HistoryRequest
import com.kylix.core.data.api.model.history.HistoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HistoryApiService {

    @POST("histories")
    suspend fun postHistory(
        @Body body: HistoryRequest
    ): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>>

    @GET("histories")
    suspend fun getHistories(): NetworkResponse<BaseResponse<List<HistoryResponse>>, BaseResponse<Unit>>
}