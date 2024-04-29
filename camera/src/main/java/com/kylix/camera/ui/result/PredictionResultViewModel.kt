package com.kylix.camera.ui.result

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.common.model.RecipeList
import com.kylix.core.repositories.favorite.FavoriteRepository
import com.kylix.core.repositories.prediction.PredictionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PredictionResultViewModel(
    private val predictionRepository: PredictionRepository,
    private val favoriteRepository: FavoriteRepository
) : BaseViewModel() {

    private val _recipes = MutableStateFlow(emptyList<RecipeList>())
    val recipes = _recipes.asStateFlow()

    fun getRelatedRecipes(query: String) {
        viewModelScope.launch {
            onDataLoading()
            predictionRepository.getRelatedRecipes(query).fold(
                ifLeft = {
                    onDataError(it.message)
                },
                ifRight = {
                    onDataSuccess()
                    _recipes.value = it.data
                }
            )
        }
    }

    fun postPredictionResult(
        prediction: String,
        actual: String,
        probability: Double,
        imageBitmap: Bitmap,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            onDataLoading()
            predictionRepository.postPredictionResult(
                prediction,
                actual,
                probability,
                imageBitmap
            ).fold(
                ifLeft = {
                    onDataError(it.message)
                },
                ifRight = {
                    onFinishLoading()
                    onSuccess()
                }
            )
        }
    }

    fun onFavoritePressed(recipeId: String) {
        val selectedItem = _recipes.value.find { it.recipeId == recipeId }
        val isFavorite = selectedItem?.isFavorite ?: return

        if (isFavorite) {
            viewModelScope.launch {
                favoriteRepository.postFavorite(recipeId).fold(
                    ifLeft = {
                        onDataError(it.message)
                    },
                    ifRight = {
                        _recipes.value = _recipes.value.map {
                            if (it.recipeId == recipeId) {
                                it.copy(isFavorite = false)
                            } else {
                                it
                            }
                        }
                    }
                )
            }
        } else {
            viewModelScope.launch {
                favoriteRepository.deleteFavorite(recipeId).fold(
                    ifLeft = {
                        onDataError(it.message)
                    },
                    ifRight = {
                        _recipes.value = _recipes.value.map {
                            if (it.recipeId == recipeId) {
                                it.copy(isFavorite = true)
                            } else {
                                it
                            }
                        }
                    }
                )
            }
        }
    }

}