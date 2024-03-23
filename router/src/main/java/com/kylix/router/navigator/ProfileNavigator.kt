package com.kylix.router.navigator

import android.app.Activity
import com.kylix.reset_password.navigation.ResetPasswordNavigation
import com.kylix.change_profile.navigation.ChangeProfileNavigation
import com.kylix.profile.navigation.ProfileNavigation

class ProfileNavigator(
    private val changeProfileNavigation: ChangeProfileNavigation,
    private val resetPasswordNavigation: ResetPasswordNavigation
): ProfileNavigation() {
    override fun navigateToChangeProfile(activity: Activity) {
        changeProfileNavigation.navigateItSelf(activity)
    }

    override fun navigateToChangePassword(activity: Activity) {
        resetPasswordNavigation.navigateItSelf(activity)
    }
}