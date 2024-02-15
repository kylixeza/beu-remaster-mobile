package com.kylix.common.widget

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.kylix.common.databinding.CommonLoadingBinding

fun Context.buildLoadingDialog() = Dialog(this).apply {
    val view = CommonLoadingBinding.inflate(layoutInflater)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(view.root)
    setCancelable(false)
    setCanceledOnTouchOutside(false)
}