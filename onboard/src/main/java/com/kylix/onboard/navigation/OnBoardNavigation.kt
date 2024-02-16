package com.kylix.onboard.navigation

import android.app.Activity
import android.content.Intent
import com.kylix.onboard.ui.OnBoardActivity

abstract class OnBoardNavigation {

    fun navigateItSelf(activity: Activity) {
        val intent = Intent(activity, OnBoardActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }

    abstract fun navigateToAuth()
}