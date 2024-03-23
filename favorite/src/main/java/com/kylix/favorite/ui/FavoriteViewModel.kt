package com.kylix.favorite.ui

import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.common.model.RecipeList
import com.kylix.core.repositories.favorite.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository
): BaseViewModel() {

    private val _favorites = MutableStateFlow(mutableListOf<RecipeList>())
    val favorites = _favorites.asStateFlow()

    fun getFavorites() {
        onDataLoading()
        viewModelScope.launch {
            favoriteRepository.getFavoriteRecipes().fold(
                ifLeft = {
                     onDataError(it.message)
                },
                ifRight = {
                    onDataSuccess()
                    _favorites.value = it.data.toMutableList()
                }
            )
        }
    }

    fun onFavoritePressed(recipeId: String) {
        val selectedItem = _favorites.value.find { it.recipeId == recipeId }
        _favorites.value = _favorites.value.apply {
            selectedItem?.let {
                remove(it)
            }
        }
        viewModelScope.launch {
            favoriteRepository.deleteFavorite(recipeId).fold(
                ifLeft = {
                    onDataError(it.message)
                    _favorites.value = _favorites.value.apply {
                        selectedItem?.let {
                            add(it)
                        }
                    }
                },
                ifRight = {

                }
            )
        }
    }
}