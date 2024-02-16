package com.kylix.splash.navigation

import android.app.Activity

abstract class SplashNavigation {

    abstract fun navigateToOnBoard(activity: Activity)
    abstract fun navigateToAuth(activity: Activity)
    abstract fun navigateToHome(activity: Activity)
}