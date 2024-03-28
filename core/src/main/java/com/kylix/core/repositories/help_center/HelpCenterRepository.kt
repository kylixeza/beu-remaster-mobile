package com.kylix.core.repositories.help_center

import arrow.core.Either
import com.kylix.common.util.Error
import com.kylix.common.util.Success

interface HelpCenterRepository {

    suspend fun postMessage(message: String): Either<Error, Success<Unit>>

}