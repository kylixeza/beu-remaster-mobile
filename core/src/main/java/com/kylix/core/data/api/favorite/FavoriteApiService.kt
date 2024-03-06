package com.kylix.core.data.api.favorite

import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteApiService {
    @POST("recipes/{recipeId}/favorites")
    suspend fun postFavoriteRecipe(
        @Path("recipeId") recipeId: String
    ): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>>

    @DELETE("recipes/{recipeId}/favorites")
    suspend fun deleteFavoriteRecipe(
        @Path("recipeId") recipeId: String
    ): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>>
}