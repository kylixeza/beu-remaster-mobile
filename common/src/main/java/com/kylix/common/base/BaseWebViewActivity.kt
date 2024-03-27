package com.kylix.common.base

import android.annotation.SuppressLint
import com.kylix.common.databinding.ActivityBaseWebViewBinding
import com.kylix.common.widget.bind

abstract class BaseWebViewActivity: BaseActivity<ActivityBaseWebViewBinding>() {

    abstract fun setUrl(): String
    abstract fun setTitle(): String

    override fun inflateViewBinding(): ActivityBaseWebViewBinding {
        return ActivityBaseWebViewBinding.inflate(layoutInflater)
    }

    override fun ActivityBaseWebViewBinding.bind() {
        webViewAppbar.bind(
            scope = this@BaseWebViewActivity,
            title = setTitle(),
            onBack = { finish() }
        )

        val url = setUrl()
        initWebView(url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(url: String){
        binding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }
        binding.webView.loadUrl(url)
    }

}