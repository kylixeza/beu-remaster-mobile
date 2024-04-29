package com.kylix.camera.navigation

import android.app.Activity

abstract class CameraNavigation {
    abstract fun navigateToDetail(activity: Activity, recipeId: String)
}