package com.kylix.router.navigator

import android.app.Activity
import com.kylix.detail.navigation.DetailNavigation
import com.kylix.favorite.navigation.FavoriteNavigation

class FavoriteNavigator(
    private val detailNavigation: DetailNavigation
): FavoriteNavigation() {
    override fun navigateToDetail(activity: Activity, recipeId: String) {
        detailNavigation.navigateItSelf(recipeId, activity)
    }

}