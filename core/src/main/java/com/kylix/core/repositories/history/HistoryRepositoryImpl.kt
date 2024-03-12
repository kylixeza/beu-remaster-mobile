package com.kylix.core.repositories.history

import arrow.core.Either
import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.common.base.NetworkOnlyResource
import com.kylix.common.util.Success
import com.kylix.common.util.Error
import com.kylix.core.data.api.history.HistoryApiService
import com.kylix.core.data.api.model.history.HistoryRequest

class HistoryRepositoryImpl(
    private val historyApiService: HistoryApiService
): HistoryRepository {
    override suspend fun postHistory(
        recipeId: String,
        timeSpent: Int
    ): Either<Error, Success<String>> {
        return object : NetworkOnlyResource<String, String>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>> {
                val body = HistoryRequest(recipeId, timeSpent)
                return historyApiService.postHistory(body)
            }

            override fun String.mapTransform(): String {
                return this
            }

        }.run()
    }
}