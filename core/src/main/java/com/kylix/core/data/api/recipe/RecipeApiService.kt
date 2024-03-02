package com.kylix.core.data.api.recipe

import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.core.data.api.model.category.CategoryResponse
import com.kylix.core.data.api.model.recipe.HomeRecipeResponse
import retrofit2.http.GET

interface RecipeApiService {

    @GET("categories")
    suspend fun getCategories(): NetworkResponse<BaseResponse<List<CategoryResponse>>, BaseResponse<Unit>>

    @GET("recipes/home")
    suspend fun getHomeRecipes(): NetworkResponse<BaseResponse<List<HomeRecipeResponse>>, BaseResponse<Unit>>
}