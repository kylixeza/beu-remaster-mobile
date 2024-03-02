package com.kylix.core.data.api.model.category

import com.kylix.common.model.Category

data class CategoryResponse(
    val categoryId: String,
    val name: String,
) {
    fun toCategory() = Category(
        categoryId = categoryId,
        name = name
    )
}
