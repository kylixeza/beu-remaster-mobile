package com.kylix.router.navigator

import android.app.Activity
import com.kylix.auth.navigation.AuthNavigation
import com.kylix.main.navigation.MainNavigation
import com.kylix.onboard.navigation.OnBoardNavigation
import com.kylix.splash.navigation.SplashNavigation

class SplashNavigator(
    private val onboardNavigation: OnBoardNavigation,
    private val authNavigation: AuthNavigation,
    private val mainNavigation: MainNavigation
): SplashNavigation() {

    override fun navigateToOnBoard(activity: Activity) {
        onboardNavigation.navigateItSelf(activity)
    }

    override fun navigateToAuth(activity: Activity) {
        authNavigation.navigateItSelf(activity)
    }

    override fun navigateToHome(activity: Activity) {
        mainNavigation.navigateItSelf(activity)
    }
}