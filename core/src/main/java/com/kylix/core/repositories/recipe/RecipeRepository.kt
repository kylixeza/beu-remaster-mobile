package com.kylix.core.repositories.recipe

import arrow.core.Either
import com.kylix.common.model.HomeRecipe
import com.kylix.common.util.Error
import com.kylix.common.util.Success

interface RecipeRepository {
    suspend fun getHomeRecipes(): Either<Error, Success<List<HomeRecipe>>>
}