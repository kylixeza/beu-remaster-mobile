package com.kylix.core.repositories.recipe

import arrow.core.Either
import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.common.base.NetworkOnlyResource
import com.kylix.common.model.HomeRecipe
import com.kylix.common.util.Error
import com.kylix.common.util.Success
import com.kylix.core.data.api.model.recipe.HomeRecipeResponse
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
}