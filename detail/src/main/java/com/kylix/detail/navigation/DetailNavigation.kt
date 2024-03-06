package com.kylix.detail.navigation

import android.app.Activity
import android.content.Intent
import com.kylix.detail.ui.DetailRecipeActivity

abstract class DetailNavigation {
    fun navigateItSelf(recipeId: String, activity: Activity) {
        val intent = Intent(activity, DetailRecipeActivity::class.java)
        intent.putExtra(DetailRecipeActivity.EXTRA_RECIPE_ID, recipeId)
        activity.startActivity(intent)
    }
}