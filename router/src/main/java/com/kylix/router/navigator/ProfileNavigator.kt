package com.kylix.router.navigator

import android.app.Activity
import com.kylix.reset_password.navigation.ResetPasswordNavigation
import com.kylix.change_profile.navigation.ChangeProfileNavigation
import com.kylix.favorite.navigation.FavoriteNavigation
import com.kylix.help_center.navigation.HelpCenterNavigation
import com.kylix.history.navigation.HistoryNavigation
import com.kylix.profile.navigation.ProfileNavigation

class ProfileNavigator(
    private val changeProfileNavigation: ChangeProfileNavigation,
    private val resetPasswordNavigation: ResetPasswordNavigation,
    private val historyNavigation: HistoryNavigation,
    private val favoriteNavigation: FavoriteNavigation,
    private val helpCenterNavigation: HelpCenterNavigation
): ProfileNavigation() {
    override fun navigateToChangeProfile(activity: Activity) {
        changeProfileNavigation.navigateItSelf(activity)
    }

    override fun navigateToResetPassword(activity: Activity) {
        resetPasswordNavigation.navigateItSelf(activity)
    }

    override fun navigateToHistory(activity: Activity) {
        historyNavigation.navigateItSelf(activity)
    }

    override fun navigateToFavorite(activity: Activity) {
        favoriteNavigation.navigateItSelf(activity)
    }

    override fun navigateToHelpCenter(activity: Activity) {
        helpCenterNavigation.navigateItSelf(activity)
    }
}