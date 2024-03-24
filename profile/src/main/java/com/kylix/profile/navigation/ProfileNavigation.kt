package com.kylix.profile.navigation

import android.app.Activity

abstract class ProfileNavigation {

    abstract fun navigateToChangeProfile(activity: Activity)
    abstract fun navigateToResetPassword(activity: Activity)
    abstract fun navigateToHistory(activity: Activity)
    abstract fun navigateToFavorite(activity: Activity)
}