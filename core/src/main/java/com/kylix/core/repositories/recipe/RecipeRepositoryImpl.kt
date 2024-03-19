package com.kylix.core.repositories.recipe

import arrow.core.Either
import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.common.base.NetworkOnlyResource
import com.kylix.common.model.HomeRecipe
import com.kylix.common.model.RecipeDetail
import com.kylix.common.model.RecipeList
import com.kylix.common.util.Error
import com.kylix.common.util.Success
import com.kylix.core.data.api.model.recipe.HomeRecipeResponse
import com.kylix.core.data.api.model.recipe.RecipeDetailResponse
import com.kylix.core.data.api.model.recipe.RecipeListResponse
import com.kylix.core.data.api.recipe.RecipeApiService

class RecipeRepositoryImpl(
    private val api: RecipeApiService
): RecipeRepository {
    override suspend fun getHomeRecipes(): Either<Error, Success<List<HomeRecipe>>> {
        return object : NetworkOnlyResource<List<HomeRecipe>, List<HomeRecipeResponse>>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<List<HomeRecipeResponse>>, BaseResponse<Unit>> {
                return api.getHomeRecipes()
            }

            override fun List<HomeRecipeResponse>.mapTransform(): List<HomeRecipe> {
                return map { it.toHomeRecipe() }
            }
        }.run()
    }

    override suspend fun searchRecipes(query: String): Either<Error, Success<List<RecipeList>>> {
        return object : NetworkOnlyResource<List<RecipeList>, List<RecipeListResponse>>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<List<RecipeListResponse>>, BaseResponse<Unit>> {
                return api.searchRecipes(query)
            }

            override fun List<RecipeListResponse>.mapTransform(): List<RecipeList> {
                return map { it.toRecipeList() }
            }
        }.run()
    }

    override suspend fun getRecipesByCategory(categoryId: String): Either<Error, Success<List<RecipeList>>> {
        return object : NetworkOnlyResource<List<RecipeList>, List<RecipeListResponse>>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<List<RecipeListResponse>>, BaseResponse<Unit>> {
                return api.getRecipesByCategory(categoryId)
            }

            override fun List<RecipeListResponse>.mapTransform(): List<RecipeList> {
                return map { it.toRecipeList() }
            }

        }.run()
    }

    override suspend fun getRecipeDetail(recipeId: String): Either<Error, Success<RecipeDetail>> {
        return object : NetworkOnlyResource<RecipeDetail, RecipeDetailResponse>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<RecipeDetailResponse>, BaseResponse<Unit>> {
                return api.getRecipeDetail(recipeId)
            }

            override fun RecipeDetailResponse.mapTransform(): RecipeDetail {
                return toRecipeDetail()
            }

        }.run()
    }
}