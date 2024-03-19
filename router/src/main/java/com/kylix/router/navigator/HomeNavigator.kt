package com.kylix.router.navigator

import android.app.Activity
import com.kylix.category.navigation.CategoryNavigation
import com.kylix.detail.navigation.DetailNavigation
import com.kylix.home.navigation.HomeNavigation
import com.kylix.search.navigation.SearchNavigation

class HomeNavigator(
    private val detailNavigation: DetailNavigation,
    private val categoryNavigation: CategoryNavigation,
    private val searchNavigation: SearchNavigation
): HomeNavigation() {
    override fun navigateToDetail(recipeId: String, activity: Activity) {
        detailNavigation.navigateItSelf(recipeId, activity)
    }

    override fun navigateToCategory(activity: Activity, categoryId: String, categoryName: String) {
        categoryNavigation.navigateItSelf(activity, categoryId, categoryName)
    }

    override fun navigateToSearch(activity: Activity) {
        searchNavigation.navigateItSelf(activity)
    }
}