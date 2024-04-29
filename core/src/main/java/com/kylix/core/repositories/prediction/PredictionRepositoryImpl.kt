package com.kylix.core.repositories.prediction

import android.graphics.Bitmap
import arrow.core.Either
import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.common.base.NetworkOnlyResource
import com.kylix.common.model.RecipeList
import com.kylix.common.util.Error
import com.kylix.common.util.Success
import com.kylix.core.data.api.model.prediction.PredictionResultRequest
import com.kylix.core.data.api.model.recipe.RecipeListResponse
import com.kylix.core.data.api.prediction.PredictionApiService
import com.kylix.core.util.toRequestBody

class PredictionRepositoryImpl(
    private val predictionApiService: PredictionApiService
): PredictionRepository {
    override suspend fun postPredictionResult(
        prediction: String,
        actual: String,
        probability: Double,
        image: Bitmap
    ): Either<Error, Success<Unit>> {
        return object : NetworkOnlyResource<Unit, String>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>> {
                val body = PredictionResultRequest(prediction, actual, probability)
                return predictionApiService.postPredictionResult(body, image.toRequestBody())
            }

            override fun String.mapTransform() {

            }
        }.run()
    }

    override suspend fun getRelatedRecipes(query: String): Either<Error, Success<List<RecipeList>>> {
        return object : NetworkOnlyResource<List<RecipeList>, List<RecipeListResponse>>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<List<RecipeListResponse>>, BaseResponse<Unit>> {
                return predictionApiService.getRelatedRecipes(query)
            }

            override fun List<RecipeListResponse>.mapTransform(): List<RecipeList> {
                return map { it.toRecipeList() }
            }

        }.run()
    }
}