package com.kylix.search.navigation

import android.app.Activity
import android.content.Intent
import com.kylix.search.ui.SearchActivity

abstract class SearchNavigation {

    fun navigateItSelf(activity: Activity) {
        val intent = Intent(activity, SearchActivity::class.java)
        activity.startActivity(intent)
    }

    abstract fun navigateToDetail(activity: Activity, recipeId: String)

}