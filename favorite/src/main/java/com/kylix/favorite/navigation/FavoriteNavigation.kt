package com.kylix.favorite.navigation

import android.app.Activity
import android.content.Intent
import com.kylix.favorite.ui.FavoriteActivity

abstract class FavoriteNavigation {
    fun navigateItSelf(activity: Activity) {
        val intent = Intent(activity, FavoriteActivity::class.java)
        activity.startActivity(intent)
    }
    abstract fun navigateToDetail(activity: Activity, recipeId: String)
}