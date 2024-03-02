package com.kylix.core.repositories.auth

import arrow.core.Either
import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.common.base.NetworkBoundRequest
import com.kylix.common.util.Error
import com.kylix.common.util.Success
import com.kylix.core.data.api.auth.AuthApiService
import com.kylix.core.data.api.model.auth.LoginRequest
import com.kylix.core.data.api.model.auth.RegisterRequest
import com.kylix.core.data.api.model.auth.TokenResponse
import com.kylix.core.data.local.preference.BeuDataStore

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val dataStore: BeuDataStore
): AuthRepository {

    override suspend fun register(
        email: String,
        password: String,
        username: String,
        phoneNumber: String,
        name: String
    ): Either<Error, Success<Unit>> {
        return object : NetworkBoundRequest<TokenResponse>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<TokenResponse>, BaseResponse<Unit>> {
                val body = RegisterRequest(
                    email = email,
                    password = password,
                    username = username,
                    phoneNumber = phoneNumber,
                    name = name
                )
                return authApiService.register(body)
            }

            override suspend fun saveCallResult(data: TokenResponse) {
                dataStore.saveToken(data.token)
            }
        }.run()
    }

    override suspend fun login(email: String, password: String): Either<Error, Success<Unit>> {
        return object : NetworkBoundRequest<TokenResponse>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<TokenResponse>, BaseResponse<Unit>> {
                val body = LoginRequest(
                    email = email,
                    password = password
                )
                return authApiService.login(body)
            }

            override suspend fun saveCallResult(data: TokenResponse) {
                dataStore.saveToken(data.token)
            }
        }.run()
    }

    override suspend fun isPassOnBoard(): Boolean {
        return dataStore.getIsPassOnBoard()
    }

    override suspend fun passOnBoard() {
        dataStore.savePassOnBoard(true)
    }

    override suspend fun isLoggedIn(): Boolean {
        return dataStore.getToken() != null
    }


}