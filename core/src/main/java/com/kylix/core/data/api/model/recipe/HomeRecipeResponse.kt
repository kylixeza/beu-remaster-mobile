package com.kylix.core.data.api.model.recipe

import com.kylix.common.model.HomeRecipe

data class HomeRecipeResponse(
    val title: String,
    val subtitle: String?,
    val recipes: List<RecipeListResponse>
) {
    fun toHomeRecipe() = HomeRecipe(
        title = title,
        subtitle = subtitle,
        recipes = recipes.map { it.toRecipeList() }
    )
}
