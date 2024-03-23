package com.kylix.profile.navigation

import android.app.Activity

abstract class ProfileNavigation {

    abstract fun navigateToChangeProfile(activity: Activity)
    abstract fun navigateToChangePassword(activity: Activity)
}