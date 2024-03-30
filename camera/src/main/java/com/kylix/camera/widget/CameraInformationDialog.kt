package com.kylix.camera.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.LinearLayout
import com.kylix.camera.databinding.DialogCameraInformationBinding
import io.dotlottie.loader.DotLottieLoader
import io.dotlottie.loader.models.DotLottie
import io.dotlottie.loader.models.DotLottieResult
import java.io.ByteArrayInputStream
import java.io.InputStream
import kotlin.math.roundToInt

fun Context.buildCameraInformationDialog() = Dialog(this).apply {
    val binding = DialogCameraInformationBinding.inflate(layoutInflater)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(binding.root)
    setCancelable(false)
    setCanceledOnTouchOutside(false)
    val metrics = resources.displayMetrics
    val width = metrics.widthPixels
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    window?.setLayout((width * 0.8).roundToInt(), LinearLayout.LayoutParams.WRAP_CONTENT)

    binding.apply {
        ivClose.setOnClickListener { dismiss() }

        val content = "onboard3.lottie"
        DotLottieLoader.with(this@buildCameraInformationDialog)
            .fromAsset(content)
            .load(object : DotLottieResult {
                override fun onError(throwable: Throwable) {

                }

                override fun onSuccess(result: DotLottie) {
                    val animation = result.animations.entries.first().value
                    val animationInputStream = ByteArrayInputStream(animation)

                    lavCameraInformation.setAnimation(animationInputStream as InputStream, "animation-${content}")
                    lavCameraInformation.playAnimation()
                }

            })
    }
}