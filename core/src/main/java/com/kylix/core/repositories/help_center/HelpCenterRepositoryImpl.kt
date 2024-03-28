package com.kylix.core.repositories.help_center

import arrow.core.Either
import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.common.base.NetworkOnlyResource
import com.kylix.common.util.Error
import com.kylix.common.util.Success
import com.kylix.core.data.api.help_center.HelpCenterApiService
import com.kylix.core.data.api.model.help_center.HelpCenterRequest

class HelpCenterRepositoryImpl(
    private val helpCenterApiService: HelpCenterApiService
): HelpCenterRepository {
    override suspend fun postMessage(message: String): Either<Error, Success<Unit>> {
        return object : NetworkOnlyResource<Unit, String>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>> {
                val body = HelpCenterRequest(message)
                return helpCenterApiService.postMessage(body)
            }

            override fun String.mapTransform() {
                return
            }

        }.run()
    }
}