package com.kylix.router.navigator

import android.app.Activity
import com.kylix.detail.navigation.DetailNavigation
import com.kylix.review.navigation.ReviewNavigation

class DetailNavigator(
    private val reviewNavigation: ReviewNavigation
): DetailNavigation() {
    override fun navigateToReview(historyId: String, activity: Activity) {
        reviewNavigation.navigateItSelf(historyId, activity)
    }

}