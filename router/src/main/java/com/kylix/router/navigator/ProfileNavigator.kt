package com.kylix.router.navigator

import android.app.Activity
import com.kylix.change_profile.navigation.ChangeProfileNavigation
import com.kylix.profile.navigation.ProfileNavigation

class ProfileNavigator(
    private val changeProfileNavigation: ChangeProfileNavigation
): ProfileNavigation() {
    override fun navigateToChangeProfile(activity: Activity) {
        changeProfileNavigation.navigateItSelf(activity)
    }
}