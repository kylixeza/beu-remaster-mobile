package com.kylix.search.ui

import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.common.model.RecipeList
import com.kylix.core.repositories.recipe.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val recipeRepository: RecipeRepository
): BaseViewModel() {

    private val _recipes = MutableStateFlow(emptyList<RecipeList>())
    val recipes = _recipes.asStateFlow()

    fun searchRecipe(query: String) {
        if (query.isEmpty()) {
            _recipes.value = emptyList()
            onDataSuccess()
            return
        }
        viewModelScope.launch {
            onDataLoading()
            recipeRepository.searchRecipes(query).fold(
                ifLeft = { onDataError(it.message) },
                ifRight = {
                    onDataSuccess()
                    _recipes.value = it.data
                }
            )
        }
    }

}