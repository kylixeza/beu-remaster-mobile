package com.kylix.router.navigator

import android.app.Activity
import com.kylix.reset_password.navigation.ResetPasswordNavigation
import com.kylix.change_profile.navigation.ChangeProfileNavigation
import com.kylix.favorite.navigation.FavoriteNavigation
import com.kylix.profile.navigation.ProfileNavigation

class ProfileNavigator(
    private val changeProfileNavigation: ChangeProfileNavigation,
    private val resetPasswordNavigation: ResetPasswordNavigation,
    private val favoriteNavigation: FavoriteNavigation
): ProfileNavigation() {
    override fun navigateToChangeProfile(activity: Activity) {
        changeProfileNavigation.navigateItSelf(activity)
    }

    override fun navigateToChangePassword(activity: Activity) {
        resetPasswordNavigation.navigateItSelf(activity)
    }

    override fun navigateToFavorite(activity: Activity) {
        favoriteNavigation.navigateItSelf(activity)
    }
}