package com.kylix.router.navigator

import android.app.Activity
import com.kylix.splash.navigation.SplashNavigation

class SplashNavigator: SplashNavigation() {

    private val onBoardNavigator by lazy { OnBoardNavigator() }

    override fun navigateToOnBoard(activity: Activity) {
        onBoardNavigator.navigateItSelf(activity)
    }

    override fun navigateToAuth(activity: Activity) {

    }

    override fun navigateToHome(activity: Activity) {

    }
}