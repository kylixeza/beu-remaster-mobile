package com.kylix.camera.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.net.Uri
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.camera2.Camera2Config
import androidx.camera.camera2.interop.Camera2CameraInfo
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraXConfig
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.kylix.camera.R
import com.kylix.camera.databinding.FragmentCameraBinding
import com.kylix.camera.tflite.TFLiteHelper
import com.kylix.camera.ui.result.PredictionResultFragment
import com.kylix.camera.widget.buildCameraInformationDialog
import com.kylix.common.base.BaseFragment
import com.kylix.common.widget.buildFullSizeImageDialog
import com.kylix.common.widget.errorSnackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileDescriptor
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CameraFragment : BaseFragment<FragmentCameraBinding>(), CameraXConfig.Provider {

    private val requestCameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            startCamera()
        } else {
            binding?.root?.errorSnackbar("Camera permission is required to use this feature")
        }
    }

    private val cameraExecutor: Executor = Executors.newSingleThreadExecutor()

    private val tfLiteHelper by lazy { TFLiteHelper(requireContext()) }
    private lateinit var imageCapture: ImageCapture

    override fun inflateViewBinding(container: ViewGroup?): FragmentCameraBinding {
        return FragmentCameraBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentCameraBinding.bind() {
        ivCameraInformation.setOnClickListener { requireContext().buildCameraInformationDialog().show() }

        val cameraPermission = android.Manifest.permission.CAMERA
        if (requireContext().checkSelfPermission(cameraPermission) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            requestCameraPermissionLauncher.launch(cameraPermission)
        }

        ivCameraCapture.setOnClickListener { takePhoto() }
    }

    @OptIn(ExperimentalCamera2Interop::class)
    @SuppressLint("RestrictedApi")
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()


        cameraProviderFuture.addListener({
            imageCapture = ImageCapture.Builder().apply {
                this.setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
            }.build()

            val imagePreview = Preview.Builder().apply {
                setTargetRotation(binding!!.previewView.display.rotation)
            }.build()

            val cameraProvider = cameraProviderFuture.get()
            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                imagePreview,
                imageCapture,
            )
            binding!!.previewView.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            imagePreview.setSurfaceProvider(binding!!.previewView.surfaceProvider)
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        val photoFile = File(
            getOutputDirectory(),
            SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
                .format(System.currentTimeMillis()) + ".jpg"
        )
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(outputOptions, cameraExecutor, object :  ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                activity?.runOnUiThread {
                    val savedUri = Uri.fromFile(photoFile)
                    val rotation = computeRelativeRotation()
                    val bitmap = savedUri.toBitmap()?.rotateBitmap(rotation.toFloat())
                    val result = tfLiteHelper.classifyImage(bitmap)
                    PredictionResultFragment(
                        actual = result.first,
                        probability = result.second,
                        imageBitmap = bitmap
                    ).show(childFragmentManager, PredictionResultFragment::class.java.simpleName)
                }
            }

            override fun onError(exception: ImageCaptureException) {
                Toast.makeText(requireContext(), "Error capturing photo: ${exception.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun systemBarColor(): Int {
        return com.kylix.common.R.color.primary_700
    }

    override fun getCameraXConfig(): CameraXConfig {
        return CameraXConfig.Builder.fromConfig(Camera2Config.defaultConfig()).apply {
            this.setMinimumLoggingLevel(android.util.Log.ERROR)
                .setCameraExecutor { cameraExecutor }
                .setAvailableCamerasLimiter(CameraSelector.DEFAULT_BACK_CAMERA)
        }.build()
    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let {
            File(it, "Beu").apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireContext().filesDir
    }

    private fun Uri.toBitmap(): Bitmap? {
        try {
            val parcelFileDescriptor = requireContext().contentResolver.openFileDescriptor(this, "r")
            val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            return image
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun Bitmap.rotateBitmap(degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(this, 0, 0, this.width, this.height, matrix, true)
    }

    @OptIn(ExperimentalCamera2Interop::class)
    private fun computeRelativeRotation(): Int {
        val cameraManager = context?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = getCameraId()
        val characteristics = cameraManager.getCameraCharacteristics(cameraId)
        val sensorOrientationDegrees = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION) ?: 0
        val sign = if (characteristics.get(CameraCharacteristics.LENS_FACING) ==
            CameraCharacteristics.LENS_FACING_FRONT
        ) 1 else -1
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val rotation = windowManager.defaultDisplay.rotation
        return (sensorOrientationDegrees - rotation * sign + 360) % 360
    }

    @OptIn(ExperimentalCamera2Interop::class)
    private fun getCameraId(): String {
        val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        val cameraProvider = ProcessCameraProvider.getInstance(requireContext()).get()
        val camera = cameraProvider.bindToLifecycle(this, cameraSelector)
        val camera2CameraInfo = Camera2CameraInfo.from(camera.cameraInfo)
        return camera2CameraInfo.cameraId
    }
}