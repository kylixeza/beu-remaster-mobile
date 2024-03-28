package com.kylix.help_center.navigation

import android.app.Activity
import android.content.Intent
import com.kylix.help_center.ui.HelpCenterActivity

abstract class HelpCenterNavigation {

    fun navigateItSelf(activity: Activity) {
        val intent = Intent(activity, HelpCenterActivity::class.java)
        activity.startActivity(intent)
    }

}