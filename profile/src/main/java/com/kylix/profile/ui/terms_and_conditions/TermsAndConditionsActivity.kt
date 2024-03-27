package com.kylix.profile.ui.terms_and_conditions

import com.kylix.common.base.BaseWebViewActivity
import com.kylix.core.BuildConfig

class TermsAndConditionsActivity: BaseWebViewActivity() {
    override fun setUrl(): String {
        return BuildConfig.BASE_URL + "/terms-and-conditions"
    }

    override fun setTitle(): String {
        return "Syarat dan Ketentuan"
    }

}