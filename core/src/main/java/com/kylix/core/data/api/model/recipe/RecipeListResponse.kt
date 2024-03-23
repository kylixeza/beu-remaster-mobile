package com.kylix.core.data.api.model.recipe

import com.google.gson.annotations.SerializedName
import com.kylix.common.model.RecipeList
import java.math.BigDecimal

data class RecipeListResponse(
    @field:SerializedName("recipe_id")
    val recipeId: String,
    val name: String,
    val difficulty: String,
    val image: String,
    val favorites: Long,
    val isFavorite: Boolean,
    val rating: Double,
    @field:SerializedName("estimation_time")
    val estimationTime: Int,
) {
    fun toRecipeList() = RecipeList(
        recipeId = recipeId,
        name = name,
        difficulty = difficulty,
        image = image,
        favorites = favorites,
        isFavorite = isFavorite,
        rating = rating,
        estimationTime = estimationTime
    )
}
