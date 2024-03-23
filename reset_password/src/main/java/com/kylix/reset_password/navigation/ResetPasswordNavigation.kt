package com.kylix.reset_password.navigation

import android.app.Activity
import android.content.Intent
import com.kylix.reset_password.ui.ResetPasswordActivity

abstract class ResetPasswordNavigation {

    fun navigateItSelf(activity: Activity) {
        val intent = Intent(activity, ResetPasswordActivity::class.java)
        activity.startActivity(intent)
    }

}