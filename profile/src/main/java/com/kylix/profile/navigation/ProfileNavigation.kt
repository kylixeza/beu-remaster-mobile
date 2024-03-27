package com.kylix.profile.navigation

import android.app.Activity
import android.content.Intent
import com.kylix.profile.ui.privacy_policy.PrivacyPolicyActivity
import com.kylix.profile.ui.terms_and_conditions.TermsAndConditionsActivity

abstract class ProfileNavigation {

    fun navigateToPrivacyPolicy(activity: Activity) {
        val intent = Intent(activity, PrivacyPolicyActivity::class.java)
        activity.startActivity(intent)
    }

    fun navigateToTermsAndConditions(activity: Activity) {
        val intent = Intent(activity, TermsAndConditionsActivity::class.java)
        activity.startActivity(intent)
    }

    abstract fun navigateToChangeProfile(activity: Activity)
    abstract fun navigateToResetPassword(activity: Activity)
    abstract fun navigateToHistory(activity: Activity)
    abstract fun navigateToFavorite(activity: Activity)
}