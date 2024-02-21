package com.kylix.router.navigator

import android.app.Activity
import com.kylix.auth.navigation.AuthNavigation
import com.kylix.main.navigation.MainNavigation

class AuthNavigator(
    private val mainNavigation: MainNavigation
): AuthNavigation() {
    override fun navigateToHome(activity: Activity) {
        mainNavigation.navigateItSelf(activity)
    }
}