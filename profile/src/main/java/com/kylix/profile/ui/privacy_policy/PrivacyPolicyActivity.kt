package com.kylix.profile.ui.privacy_policy

import com.kylix.common.base.BaseWebViewActivity
import com.kylix.core.BuildConfig

class PrivacyPolicyActivity: BaseWebViewActivity() {
    override fun setUrl(): String {
        return BuildConfig.BASE_URL + "/privacy-policy"
    }

    override fun setTitle(): String {
        return "Kebijakan Privasi"
    }
}