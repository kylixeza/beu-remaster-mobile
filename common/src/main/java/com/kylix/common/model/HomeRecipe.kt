package com.kylix.common.model

data class HomeRecipe(
    val title: String,
    val subtitle: String?,
    val recipes: List<RecipeList>
)
