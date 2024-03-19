package com.kylix.core.repositories.profile

import arrow.core.Either
import com.kylix.common.util.Success
import com.kylix.common.util.Error

interface ProfileRepository {

    suspend fun greet(): Either<Error, Success<String>>
}