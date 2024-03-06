package com.kylix.router.navigator

import android.app.Activity
import com.kylix.detail.navigation.DetailNavigation
import com.kylix.home.navigation.HomeNavigation

class HomeNavigator(
    private val detailNavigation: DetailNavigation
): HomeNavigation() {
    override fun navigateToDetail(recipeId: String, activity: Activity) {
        detailNavigation.navigateItSelf(recipeId, activity)
    }
}