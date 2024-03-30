package com.kylix.camera.ui

import android.graphics.BitmapFactory
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.kylix.camera.R
import com.kylix.camera.databinding.FragmentCameraBinding
import com.kylix.camera.widget.buildCameraInformationDialog
import com.kylix.common.base.BaseFragment
import com.kylix.common.widget.buildFullSizeImageDialog
import com.kylix.common.widget.errorSnackbar
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CameraFragment : BaseFragment<FragmentCameraBinding>() {

    private val requestCameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            startCamera()
        } else {
            binding?.root?.errorSnackbar("Camera permission is required to use this feature")
        }
    }
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

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        cameraProviderFuture.addListener({
            val imagePreview = Preview.Builder().apply {
                setTargetRotation(binding!!.previewView.display.rotation)
            }.build()

            imageCapture = ImageCapture.Builder().apply {
                setFlashMode(ImageCapture.FLASH_MODE_AUTO)
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
        imageCapture.takePicture(Executors.newSingleThreadExecutor(), object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                val buffer = image.planes[0].buffer
                val bytes = ByteArray(buffer.capacity())
                buffer.get(bytes)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                activity?.runOnUiThread {
                    requireContext().buildFullSizeImageDialog(bitmap).show()
                }
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                Toast.makeText(requireContext(), "Error capturing photo: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun systemBarColor(): Int {
        return android.R.color.transparent
    }
}