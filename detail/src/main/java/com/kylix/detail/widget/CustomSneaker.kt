package com.kylix.detail.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.view.ViewGroup
import com.irozon.sneaker.Sneaker
import com.kylix.recipe.R
import com.kylix.recipe.databinding.SuccessSneakerBinding

class CustomSneaker(private val activity: Activity) {


    private val sneaker = Sneaker.with(activity).autoHide(false).setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
    private val successSneak = SuccessSneakerBinding.inflate(activity.layoutInflater)

    init {
        sneaker.sneakCustom(successSneak.root)
    }

    fun showSuccessSneaker(
        calculatedTime: String,
        onButtonPressed: () -> Unit
    ) {
        successSneak.root.layoutParams.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            if (this is ViewGroup.MarginLayoutParams) {
                topMargin = getStatusBarHeight()
            }
        }
        successSneak.tvMessage.text = activity.resources.getString(R.string.cook_for, calculatedTime)
        successSneak.btnReviewNow.setOnClickListener {
            onButtonPressed()
            sneaker.hide()
        }
        successSneak.ivClose.setOnClickListener { sneaker.hide() }
    }

    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = activity.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}