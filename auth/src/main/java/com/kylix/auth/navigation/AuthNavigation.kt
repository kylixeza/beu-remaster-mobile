package com.kylix.auth.navigation

import android.app.Activity
import android.content.Intent
import com.kylix.auth.ui.AuthActivity

abstract class AuthNavigation {

    fun navigateItSelf(activity: Activity) {
        val intent = Intent(activity, AuthActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }

    abstract fun navigateToHome(activity: Activity)
}