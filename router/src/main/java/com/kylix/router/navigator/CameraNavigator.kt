package com.kylix.router.navigator

import android.app.Activity
import com.kylix.camera.navigation.CameraNavigation
import com.kylix.detail.navigation.DetailNavigation

class CameraNavigator(
    private val detailNavigation: DetailNavigation
): CameraNavigation() {
    override fun navigateToDetail(activity: Activity, recipeId: String) {
        detailNavigation.navigateItSelf(recipeId, activity)
    }
}