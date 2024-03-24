package com.kylix.history.navigation

import android.app.Activity
import android.content.Intent
import com.kylix.history.ui.HistoryActivity

abstract class HistoryNavigation {

    fun navigateItSelf(activity: Activity) {
        val intent = Intent(activity, HistoryActivity::class.java)
        activity.startActivity(intent)
    }

    abstract fun navigateToReview(activity: Activity, historyId: String)

}