package com.kylix.core.repositories.favorite

import arrow.core.Either
import com.kylix.common.util.Success
import com.kylix.common.util.Error

interface FavoriteRepository {

    suspend fun postFavorite(recipeId: String): Either<Error, Success<Unit>>
    suspend fun deleteFavorite(recipeId: String): Either<Error, Success<Unit>>

}