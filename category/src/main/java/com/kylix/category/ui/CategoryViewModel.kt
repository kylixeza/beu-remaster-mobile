package com.kylix.category.ui

import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.common.model.RecipeList
import com.kylix.core.repositories.recipe.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val recipeRepository: RecipeRepository
): BaseViewModel() {

    private val _categoryName = MutableStateFlow("")
    val categoryName = _categoryName.asStateFlow()

    private val _recipes = MutableStateFlow(emptyList<RecipeList>())
    val recipes = _recipes.asStateFlow()

    fun getRecipes(intent: Intent) {
        onDataLoading()
        val categoryId = intent.getStringExtra(CategoryActivity.EXTRA_CATEGORY_ID).orEmpty()
        viewModelScope.launch {
            recipeRepository.getRecipesByCategory(categoryId).fold(
                ifLeft = { onDataError(it.message) },
                ifRight = {
                    onDataSuccess()
                    _recipes.value = it.data
                }
            )
        }
    }

    fun getCategoryName(intent: Intent) {
        _categoryName.value = intent.getStringExtra(CategoryActivity.EXTRA_CATEGORY_NAME).orEmpty()
    }

}