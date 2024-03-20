package com.kylix.core.repositories.profile

import arrow.core.Either
import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.common.base.NetworkOnlyResource
import com.kylix.common.model.User
import com.kylix.common.util.Error
import com.kylix.common.util.Success
import com.kylix.core.data.api.model.user.UserResponse
import com.kylix.core.data.api.profile.ProfileApiService

class ProfileRepositoryImpl(
    private val profileApiService: ProfileApiService
) : ProfileRepository {

    override suspend fun greet(): Either<Error, Success<String>> {
        return object : NetworkOnlyResource<String, String>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>> {
                return profileApiService.greet()
            }

            override fun String.mapTransform(): String {
                return this
            }
        }.run()
    }

    override suspend fun getProfile(): Either<Error, Success<User>> {
        return object : NetworkOnlyResource<User, UserResponse>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<UserResponse>, BaseResponse<Unit>> {
                return profileApiService.getProfile()
            }

            override fun UserResponse.mapTransform(): User {
                return this.toUser()
            }
        }.run()
    }
}