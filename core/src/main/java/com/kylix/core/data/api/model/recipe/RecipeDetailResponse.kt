package com.kylix.core.data.api.model.recipe

import com.google.gson.annotations.SerializedName
import com.kylix.common.model.RecipeDetail
import com.kylix.core.data.api.model.nutrition.NutritionResponse
import com.kylix.core.data.api.model.review.ReviewResponse
import java.math.BigDecimal

data class RecipeDetailResponse(
    @field:SerializedName("recipe_id")
    val recipeId: String,
    @field:SerializedName("is_favorite")
    val isFavorite: Boolean,
    val name: String,
    val video: String,
    val ingredients: List<String>,
    val tools: List<String>,
    val steps: List<String>,
    @field:SerializedName("average_rating")
    val averageRating: Double,
    @field:SerializedName("average_count")
    val averageCount: Long,
    val description: String,
    @field:SerializedName("estimate_time")
    val estimateTime: String,
    @field:SerializedName("comments_count")
    val commentsCount: Int,
    @field:SerializedName("nutrition_information")
    val nutritionInformation: List<NutritionResponse>,
    val reviews: List<ReviewResponse>
) {
    fun toRecipeDetail() = RecipeDetail(
        recipeId = recipeId,
        isFavorite = isFavorite,
        name = name,
        video = video,
        ingredients = ingredients,
        tools = tools,
        steps = steps,
        averageRating = averageRating,
        averageCount = averageCount,
        description = description,
        estimateTime = estimateTime,
        commentsCount = commentsCount,
        nutritionInformation = nutritionInformation.map { it.toNutrition() },
        reviews = reviews.map { it.toReview() }
    )
}
