package com.kylix.change_profile.navigation

import android.app.Activity
import android.content.Intent
import com.kylix.change_profile.ui.ChangeProfileActivity

abstract class ChangeProfileNavigation {

    fun navigateItSelf(activity: Activity) {
        val intent = Intent(activity, ChangeProfileActivity::class.java)
        activity.startActivity(intent)
    }

}