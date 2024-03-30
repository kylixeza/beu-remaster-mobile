package com.kylix.update_profile.navigation

import android.app.Activity
import android.content.Intent
import com.kylix.update_profile.ui.UpdateProfileActivity

abstract class UpdateProfileNavigation {

    fun navigateItSelf(activity: Activity) {
        val intent = Intent(activity, UpdateProfileActivity::class.java)
        activity.startActivity(intent)
    }

}