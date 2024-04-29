package com.kylix.camera.ui

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.common.model.RecipeList
import com.kylix.core.repositories.favorite.FavoriteRepository
import com.kylix.core.repositories.prediction.PredictionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CameraViewModel(
    private val predictionRepository: PredictionRepository,
    private val favoriteRepository: FavoriteRepository
) : BaseViewModel() {

    private val _recipes = MutableStateFlow(emptyList<RecipeList>())
    val recipes = _recipes.asStateFlow()

    private val _imageBitmap = MutableStateFlow<Bitmap?>(null)
    val imageBitmap = _imageBitmap.asStateFlow()

    private val _actual = MutableStateFlow("")
    val actual = _actual.asStateFlow()

    private val _probability = MutableStateFlow(0.0)
    val probability = _probability.asStateFlow()

    fun setActual(actual: String) {
        _actual.value = actual
    }

    fun setProbability(probability: Double) {
        _probability.value = probability
    }

    fun setImageBitmap(bitmap: Bitmap?) {
        _imageBitmap.value = bitmap
    }

}