package com.kylix.router.navigator

import android.app.Activity
import com.kylix.detail.navigation.DetailNavigation
import com.kylix.search.navigation.SearchNavigation

class SearchNavigator(
    private val detailNavigation: DetailNavigation
): SearchNavigation() {
    override fun navigateToDetail(activity: Activity, recipeId: String) {
        detailNavigation.navigateItSelf(recipeId, activity)
    }
}