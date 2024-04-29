package com.kylix.camera.widget

import android.R
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.kylix.camera.databinding.DialogPredictionSendBinding
import com.kylix.common.util.draw
import kotlin.math.roundToInt

fun Context.buildPredictionSendDialog(
    actual: String,
    probability: Double,
    bitmap: Bitmap?,
    onButtonPressed: Dialog.(String) -> Unit
) = Dialog(this).apply {
    val binding = DialogPredictionSendBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val metrics = resources.displayMetrics
    val width = metrics.widthPixels
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    window?.setLayout((width * 0.8).roundToInt(), LinearLayout.LayoutParams.WRAP_CONTENT)

    val expectations = readLinesFromAssets(context, "beu_labels.txt")
    binding.actvPrediction.setAdapter(
        ArrayAdapter(
            this@buildPredictionSendDialog,
            R.layout.simple_dropdown_item_1line,
            expectations
        )
    )

    binding.tietActual.setText(actual)
    binding.tvAccuracy.text = "Accuracy: $probability"
    binding.ivPreview.draw(binding.root.context, bitmap)

    binding.btnSubmit.setOnClickListener {
        onButtonPressed(binding.actvPrediction.text.toString())
    }
}

private fun readLinesFromAssets(context: Context, filename: String): List<String> {
    return try {
        val inputStream = context.assets.open(filename)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val text = String(buffer, Charsets.UTF_8)
        text.split("\n")
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList() // Return an empty list if an error occurs
    }
}