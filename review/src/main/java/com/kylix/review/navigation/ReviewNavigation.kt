package com.kylix.review.navigation

import android.app.Activity
import android.content.Intent
import com.kylix.review.ui.ReviewActivity

abstract class ReviewNavigation {
    fun navigateItSelf(historyId: String, activity: Activity) {
        val intent = Intent(activity, ReviewActivity::class.java)
        intent.putExtra(ReviewActivity.EXTRA_HISTORY_ID, historyId)
        activity.startActivity(intent)
        activity.finish()
    }
}