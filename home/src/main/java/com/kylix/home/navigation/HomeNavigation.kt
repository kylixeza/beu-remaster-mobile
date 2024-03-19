package com.kylix.home.navigation

import android.app.Activity

abstract class HomeNavigation {

    abstract fun navigateToDetail(recipeId: String, activity: Activity)
    abstract fun navigateToCategory(activity: Activity, categoryId: String, categoryName: String)
    abstract fun navigateToSearch(activity: Activity)
}