package com.kylix.core.repositories.prediction

import android.graphics.Bitmap
import arrow.core.Either
import com.kylix.common.model.RecipeList
import com.kylix.common.util.Error
import com.kylix.common.util.Success

interface PredictionRepository {
    suspend fun postPredictionResult(
        prediction: String,
        actual: String,
        probability: Double,
        image: Bitmap
    ): Either<Error, Success<Unit>>

    suspend fun getRelatedRecipes(query: String): Either<Error, Success<List<RecipeList>>>
}