package com.kylix.router.navigator

import android.app.Activity
import com.kylix.auth.navigation.AuthNavigation
import com.kylix.onboard.navigation.OnBoardNavigation
import com.kylix.splash.navigation.SplashNavigation

class SplashNavigator(
    private val onboardNavigation: OnBoardNavigation,
    private val authNavigation: AuthNavigation
): SplashNavigation() {

    override fun navigateToOnBoard(activity: Activity) {
        onboardNavigation.navigateItSelf(activity)
    }

    override fun navigateToAuth(activity: Activity) {
        authNavigation.apply { activity.navigateItSelf() }
    }

    override fun navigateToHome(activity: Activity) {

    }
}