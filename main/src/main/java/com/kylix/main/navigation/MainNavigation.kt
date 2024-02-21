package com.kylix.main.navigation

import android.app.Activity
import android.content.Intent
import com.kylix.main.MainActivity

open class MainNavigation {

    fun navigateItSelf(activity: Activity) {
        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }

}