package com.kylix.detail.ui

import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.common.model.RecipeDetail
import com.kylix.common.util.TimerBenchmark
import com.kylix.core.repositories.favorite.FavoriteRepository
import com.kylix.core.repositories.history.HistoryRepository
import com.kylix.core.repositories.recipe.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailRecipeViewModel(
    private val recipeRepository: RecipeRepository,
    private val favoriteRepository: FavoriteRepository,
    private val historyRepository: HistoryRepository
): BaseViewModel() {

    private val _recipe = MutableStateFlow<RecipeDetail?>(null)
    val recipe = _recipe.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private val _elapsedTime = MutableStateFlow("")
    val elapsedTime = _elapsedTime.asStateFlow()

    private val _historyId = MutableStateFlow("")
    val historyId = _historyId.asStateFlow()

    private val timerBenchmark = TimerBenchmark()

    fun getRecipeDetail(intent: Intent) {
        timerBenchmark.start()
        viewModelScope.launch {
            val recipeId = intent.getStringExtra(DetailRecipeActivity.EXTRA_RECIPE_ID).orEmpty()
            onDataLoading()
            val result = recipeRepository.getRecipeDetail(recipeId)
            result.fold(
                ifLeft = {
                    onDataError(it.message)
                },
                ifRight = {
                    _recipe.value = it.data
                    _isFavorite.value = it.data.isFavorite
                    onDataSuccess()
                }
            )
        }
    }

    fun onStopTimer() {
        timerBenchmark.stop()
        val elapsedTime = timerBenchmark.elapsedTime(TimerBenchmark.TimeUnit.SECONDS)

        viewModelScope.launch {
            historyRepository.postHistory(
                recipeId = _recipe.value?.recipeId.orEmpty(),
                timeSpent = elapsedTime
            ).fold(
                ifLeft = {
                    onDataError(it.message)
                },
                ifRight = {
                    _historyId.value = it.data
                    _elapsedTime.value = timerBenchmark.convertToMinutes(elapsedTime)
                }
            )
        }
    }

    fun onFavoriteIconPressed() {
        viewModelScope.launch {
            val recipeId = _recipe.value?.recipeId ?: return@launch
            val isFavorite = _isFavorite.value
            val result = if (isFavorite) {
                favoriteRepository.deleteFavorite(recipeId)
            } else {
                favoriteRepository.postFavorite(recipeId)
            }

            result.fold(
                ifLeft = {
                    onDataError(it.message)
                },
                ifRight = {
                    _isFavorite.value = !isFavorite
                }
            )
        }
    }

}