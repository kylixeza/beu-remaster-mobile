package com.kylix.core.data.api.model.history

import com.google.gson.annotations.SerializedName

data class HistoryRequest(
    @field:SerializedName("recipe_id")
    val recipeId: String,
    val spendTime: Int,
)