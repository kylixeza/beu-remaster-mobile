package com.kylix.core.repositories.history

import arrow.core.Either
import com.kylix.common.util.Success
import com.kylix.common.util.Error

interface HistoryRepository {

    suspend fun postHistory(
        recipeId: String,
        timeSpent: Int,
    ): Either<Error, Success<String>>

}