package com.kylix.router.navigator

import android.app.Activity
import com.kylix.history.navigation.HistoryNavigation
import com.kylix.review.navigation.ReviewNavigation

class HistoryNavigator(
    private val reviewNavigation: ReviewNavigation,
): HistoryNavigation() {
    override fun navigateToReview(activity: Activity, historyId: String) {
        reviewNavigation.navigateItSelf(historyId, activity)
    }
}