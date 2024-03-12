package com.kylix.common.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.kylix.common.databinding.ImageFullSizeDialogBinding
import com.kylix.common.util.draw
import kotlin.math.roundToInt

private fun Context.buildBaseFullSizeImageDialog(
    drawImage: ImageFullSizeDialogBinding.() -> Unit
) = Dialog(this).apply {
    val view = ImageFullSizeDialogBinding.inflate(layoutInflater)
    drawImage.invoke(view)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(view.root)

    val metrics = resources.displayMetrics
    val width = metrics.widthPixels
    val height = metrics.heightPixels
    val ratio = 0.95

    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    window?.setLayout((width * ratio).roundToInt(), (height * ratio).roundToInt())
}

fun Context.buildFullSizeImageDialog(image: Bitmap) = buildBaseFullSizeImageDialog {
    root.draw(this@buildFullSizeImageDialog, image)
}


fun Context.buildFullSizeImageDialog(image: String) = buildBaseFullSizeImageDialog {
    root.draw(this@buildFullSizeImageDialog, image)
}
