package com.kylix.home

import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.common.model.Category
import com.kylix.common.model.HomeRecipe
import com.kylix.core.repositories.category.CategoryRepository
import com.kylix.core.repositories.recipe.RecipeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val recipeRepository: RecipeRepository
): BaseViewModel() {

    private val _categories = MutableStateFlow(emptyList<Category>())
    val categories = _categories.asStateFlow()

    private val _homeRecipes = MutableStateFlow(emptyList<HomeRecipe>())
    val homeRecipes = _homeRecipes.asStateFlow()

    init {
        viewModelScope.launch {
            onDataLoading()
            async { getCategories() }.await()
            async { getHomeRecipes() }.await()
            onDataSuccess()
        }
    }

    private suspend fun getCategories() {
        categoryRepository.getCategories().fold(
            ifLeft = { error ->
                 onDataError(error.message)
            },
            ifRight = { success ->
                _categories.value = success.data
            }
        )
    }

    private suspend fun getHomeRecipes() {
        recipeRepository.getHomeRecipes().fold(
            ifLeft = { error ->
                 onDataError(error.message)
            },
            ifRight = { success ->
                _homeRecipes.value = success.data
            }
        )
    }


}