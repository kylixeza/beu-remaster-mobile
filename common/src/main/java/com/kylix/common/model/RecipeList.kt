package com.kylix.common.model

data class RecipeList(
    val recipeId: String,
    val name: String,
    val difficulty: String,
    val image: String,
    val favorites: Long,
    val rating: Double,
    val estimationTime: Int,
)
