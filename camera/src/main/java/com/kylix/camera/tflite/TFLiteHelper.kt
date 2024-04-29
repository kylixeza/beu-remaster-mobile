package com.kylix.camera.tflite

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.TensorOperator
import org.tensorflow.lite.support.common.TensorProcessor
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.Collections

class TFLiteHelper(
    private val context: Context
) {
    private lateinit var tflite: Interpreter
    
    companion object {
        private const val MODEL_PATH = "beu_mini_v2.tflite"
        private const val LABELS_PATH = "beu_labels.txt"
    }
    
    init {
        try {
            val opt = Interpreter.Options()
            tflite = loadModelFile(context as Activity)?.let { Interpreter(it, opt) } ?: throw Exception("Invalid model file")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    fun classifyImage(bitmap: Bitmap?): Pair<String, Float> {
        val imageTensorIndex = 0
        val imageShape = tflite.getInputTensor(imageTensorIndex).shape() // {1, height, width, 3}
        val imageDataType: DataType = tflite.getInputTensor(imageTensorIndex).dataType()
        val probabilityTensorIndex = 0
        val probabilityShape =
            tflite.getOutputTensor(probabilityTensorIndex).shape() // {1, NUM_CLASSES}
        val probabilityDataType: DataType =
            tflite.getOutputTensor(probabilityTensorIndex).dataType()

        val inputImageBuffer = TensorImage(imageDataType)
            .loadImage(imageShape, bitmap ?: return Pair("", 0.0f))
        val outputProbabilityBuffer = TensorBuffer.createFixedSize(probabilityShape, probabilityDataType)
        val probabilityProcessor = TensorProcessor.Builder().build()
        tflite.run(inputImageBuffer!!.buffer, outputProbabilityBuffer.buffer.rewind())

        val labels = try {
            FileUtil.loadLabels(context, LABELS_PATH)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return Pair("", 0.0f)
        }
        val labeledProbability =
            labels.let {
                TensorLabel(it, probabilityProcessor!!.process(outputProbabilityBuffer))
                    .mapWithFloatValue
            }
        val maxValueInMap: Float = Collections.max(labeledProbability.values)
        val found = labeledProbability.entries.find { it.value == maxValueInMap }
        return found?.key.orEmpty() to (found?.value ?: 0.0f)
    }
    
    private fun loadModelFile(activity: Activity): MappedByteBuffer? {
        val fileDescriptor = activity.assets.openFd(MODEL_PATH)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel: FileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
    
    private fun TensorImage.loadImage(imageShape: IntArray, bitmap: Bitmap): TensorImage? {
        this.load(bitmap)
        val imageProcessor: ImageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(imageShape[1], imageShape[2], ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(getPreprocessNormalizeOp())
            .build()
        return imageProcessor.process(this)
    }
    
    private fun getPreprocessNormalizeOp(): TensorOperator {
        return NormalizeOp(0f, 255f)
    }
}