package com.kylix.core.repositories.favorite

import arrow.core.Either
import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.common.base.NetworkBoundRequest
import com.kylix.common.util.Error
import com.kylix.common.util.Success
import com.kylix.core.data.api.favorite.FavoriteApiService

class FavoriteRepositoryImpl(
    private val apiService: FavoriteApiService
): FavoriteRepository {
    override suspend fun postFavorite(recipeId: String): Either<Error, Success<Unit>> {
        return object : NetworkBoundRequest<String>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>> {
                return apiService.postFavoriteRecipe(recipeId)
            }
        }.run()
    }

    override suspend fun deleteFavorite(recipeId: String): Either<Error, Success<Unit>> {
        return object : NetworkBoundRequest<String>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>> {
                return apiService.deleteFavoriteRecipe(recipeId)
            }
        }.run()
    }
}