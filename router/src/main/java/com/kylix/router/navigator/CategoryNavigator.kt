package com.kylix.router.navigator

import android.app.Activity
import com.kylix.category.navigation.CategoryNavigation
import com.kylix.detail.navigation.DetailNavigation

class CategoryNavigator(
    private val detailNavigation: DetailNavigation
): CategoryNavigation() {
    override fun navigateToDetail(activity: Activity, recipeId: String) {
        detailNavigation.navigateItSelf(recipeId, activity)
    }
}