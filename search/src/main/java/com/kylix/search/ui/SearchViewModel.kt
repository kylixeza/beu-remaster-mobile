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

    private val _recipes = MutableStateFlow<List<RecipeList>?>(null)
    val recipes = _recipes.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private fun searchRecipe() {
        if (_query.value.isEmpty()) {
            _recipes.value = null
            onDataSuccess()
            return
        }
        viewModelScope.launch {
            onDataLoading()
            recipeRepository.searchRecipes(_query.value).fold(
                ifLeft = { onDataError(it.message) },
                ifRight = {
                    onDataSuccess()
                    _recipes.value = it.data
                }
            )
        }
    }

    fun setQuery(query: String) {
        _query.value = query
        searchRecipe()
    }

}