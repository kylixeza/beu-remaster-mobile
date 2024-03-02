package com.kylix.core.repositories.category

import arrow.core.Either
import com.kylix.common.model.Category
import com.kylix.common.util.Error
import com.kylix.common.util.Success

interface CategoryRepository {
    suspend fun getCategories(): Either<Error, Success<List<Category>>>
}