package com.kylix.core.data.api.model.history

import com.google.gson.annotations.SerializedName
import com.kylix.common.model.History

data class HistoryResponse(
    @field:SerializedName("history_id")
    val historyId: String,
    @field:SerializedName("recipe_name")
    val recipeName: String,
    @field:SerializedName("recipe_image")
    val recipeImage: String,
    @field:SerializedName("time_stamp")
    val timeStamp: String,
    @field:SerializedName("spend_time")
    val spendTime: String,
    @field:SerializedName("is_reviewed")
    val isReviewed: Boolean,
    @field:SerializedName("review_rating")
    val reviewRating: Int,
) {
    fun toHistory() = History(
        historyId = historyId,
        recipeName = recipeName,
        recipeImage = recipeImage,
        timeStamp = timeStamp,
        spendTime = spendTime,
        isReviewed = isReviewed,
        reviewRating = reviewRating,
    )
}