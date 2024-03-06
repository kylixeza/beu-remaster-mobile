package com.kylix.core.data.api.model.nutrition

import com.kylix.common.model.Nutrition

data class NutritionResponse(
    val nutritionId: String,
    val name: String,
    val amount: String
) {
    fun toNutrition() = Nutrition(
        nutritionId = nutritionId,
        name = name,
        amount = amount
    )
}
