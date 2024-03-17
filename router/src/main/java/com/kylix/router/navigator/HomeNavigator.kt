package com.kylix.router.navigator

import android.app.Activity
import com.kylix.category.navigation.CategoryNavigation
import com.kylix.detail.navigation.DetailNavigation
import com.kylix.home.navigation.HomeNavigation

class HomeNavigator(
    private val detailNavigation: DetailNavigation,
    private val categoryNavigation: CategoryNavigation
): HomeNavigation() {
    override fun navigateToDetail(recipeId: String, activity: Activity) {
        detailNavigation.navigateItSelf(recipeId, activity)
    }

    override fun navigateToCategory(activity: Activity, categoryId: String, categoryName: String) {
        categoryNavigation.navigateItSelf(activity, categoryId, categoryName)
    }
}