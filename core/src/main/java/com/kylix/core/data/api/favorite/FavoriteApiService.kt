package com.kylix.core.data.api.favorite

import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.core.data.api.model.recipe.RecipeListResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
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

    @GET("favorites")
    suspend fun getFavoriteRecipes(): NetworkResponse<BaseResponse<List<RecipeListResponse>>, BaseResponse<Unit>>
}