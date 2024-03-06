package com.kylix.core.data.api.recipe

import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.core.data.api.model.category.CategoryResponse
import com.kylix.core.data.api.model.recipe.HomeRecipeResponse
import com.kylix.core.data.api.model.recipe.RecipeDetailResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeApiService {

    @GET("categories")
    suspend fun getCategories(): NetworkResponse<BaseResponse<List<CategoryResponse>>, BaseResponse<Unit>>

    @GET("recipes/home")
    suspend fun getHomeRecipes(): NetworkResponse<BaseResponse<List<HomeRecipeResponse>>, BaseResponse<Unit>>

    @GET("recipes/{recipeId}")
    suspend fun getRecipeDetail(
        @Path("recipeId") recipeId: String
    ): NetworkResponse<BaseResponse<RecipeDetailResponse>, BaseResponse<Unit>>
}