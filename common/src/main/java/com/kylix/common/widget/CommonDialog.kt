package com.kylix.common.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.LinearLayout
import com.kylix.common.R
import com.kylix.common.databinding.CommonBaseDialogBinding
import com.kylix.common.databinding.CommonLoadingBinding
import com.kylix.common.util.draw

fun Context.buildLoadingDialog() = Dialog(this).apply {
    val view = CommonLoadingBinding.inflate(layoutInflater)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(view.root)
    setCancelable(false)
    setCanceledOnTouchOutside(false)

    val metrics = resources.displayMetrics
    val width = metrics.widthPixels
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    window?.setLayout(6 * width / 7, LinearLayout.LayoutParams.WRAP_CONTENT)
}

fun Context.buildSuccessDialog(
    title: String,
    message: String,
    buttonText: String,
    onDismiss: () -> Unit
) = Dialog(this).apply {
    val view = CommonBaseDialogBinding.inflate(layoutInflater)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(view.root)
    setCancelable(false)
    setCanceledOnTouchOutside(false)

    val metrics = resources.displayMetrics
    val width = metrics.widthPixels
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    window?.setLayout(6 * width / 7, LinearLayout.LayoutParams.WRAP_CONTENT)

    view.apply {
        cvDialog.setCardBackgroundColor(getColor(R.color.success_900))
        ivIcon.draw(R.drawable.ic_base_success)
        btnClose.text = buttonText
        tvTitle.text = title
        tvMessage.text = message
        btnClose.setOnClickListener {
            dismiss()
            onDismiss()
        }
    }
}

fun Context.buildErrorDialog(
    title: String,
    message: String,
    buttonText: String,
    onDismiss: () -> Unit
) = Dialog(this).apply {
    val view = CommonBaseDialogBinding.inflate(layoutInflater)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(view.root)
    setCancelable(false)
    setCanceledOnTouchOutside(false)

    val metrics = resources.displayMetrics
    val width = metrics.widthPixels
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    window?.setLayout(6 * width / 7, LinearLayout.LayoutParams.WRAP_CONTENT)

    view.apply {
        cvDialog.setCardBackgroundColor(getColor(R.color.error_900))
        ivIcon.draw(R.drawable.ic_base_error)
        btnClose.text = buttonText
        tvTitle.text = title
        tvMessage.text = message
        btnClose.setOnClickListener {
            onDismiss()
            dismiss()
        }
    }
}