package com.kylix.core.repositories.category

import arrow.core.Either
import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.common.base.NetworkOnlyResource
import com.kylix.common.model.Category
import com.kylix.common.util.Error
import com.kylix.common.util.Success
import com.kylix.core.data.api.model.category.CategoryResponse
import com.kylix.core.data.api.recipe.RecipeApiService

class CategoryRepositoryImpl(
    private val recipeApiService: RecipeApiService,
): CategoryRepository {
    override suspend fun getCategories(): Either<Error, Success<List<Category>>> =
        object : NetworkOnlyResource<List<Category>, List<CategoryResponse>>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<List<CategoryResponse>>, BaseResponse<Unit>> {
                return recipeApiService.getCategories()
            }

            override fun List<CategoryResponse>.mapTransform(): List<Category> {
                return map {it.toCategory() }
            }
        }.run()

}