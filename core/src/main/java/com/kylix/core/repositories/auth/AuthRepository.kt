package com.kylix.core.repositories.auth

import arrow.core.Either
import com.kylix.common.util.Error
import com.kylix.common.util.Success

interface AuthRepository {

    suspend fun register(
        password: String,
        username: String,
        phoneNumber: String,
        name: String
    ): Either<Error, Success<Unit>>

    suspend fun login(
        identifier: String,
        password: String
    ): Either<Error, Success<Unit>>

    suspend fun logout(): Either<Error, Success<Unit>>

    suspend fun isPassOnBoard(): Boolean
    suspend fun passOnBoard()

    suspend fun isLoggedIn(): Boolean
}