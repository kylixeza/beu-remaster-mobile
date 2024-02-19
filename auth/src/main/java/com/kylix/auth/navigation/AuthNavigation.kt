package com.kylix.auth.navigation

import android.app.Activity
import android.content.Intent
import com.kylix.auth.ui.AuthActivity

abstract class AuthNavigation {

    fun Activity.navigateItSelf() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    abstract fun Activity.navigateToHome()
}