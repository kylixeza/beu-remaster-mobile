package com.kylix.router.navigator

import android.app.Activity
import com.kylix.auth.navigation.AuthNavigation
import com.kylix.onboard.navigation.OnBoardNavigation

class OnBoardNavigator(
    private val authNavigation: AuthNavigation
): OnBoardNavigation() {

    override fun navigateToAuth(activity: Activity) {
        authNavigation.apply { activity.navigateItSelf() }
    }
}