package com.kylix.review.ui

import android.graphics.Bitmap
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.kylix.common.base.BaseActivity
import com.kylix.common.util.ScreenOrientation
import com.kylix.common.util.dispose
import com.kylix.common.util.show
import com.kylix.common.widget.bind
import com.kylix.common.widget.buildErrorDialog
import com.kylix.common.widget.buildFullSizeImageDialog
import com.kylix.common.widget.buildSuccessDialog
import com.kylix.review.adapter.ImagesAdapter
import com.kylix.review.databinding.ActivityReviewBinding
import com.kylix.review.util.CameraManager
import com.kylix.review.widget.bind
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.widget.textChanges

@OptIn(InternalCoroutinesApi::class)
class ReviewActivity : BaseActivity<ActivityReviewBinding>() {

    override val viewModel by viewModel<ReviewViewModel>()

    private val imagesAdapter by lazy { ImagesAdapter(
        outsidePadding = binding.llReview.paddingStart + binding.llReview.paddingEnd,
        onImageDeleted = { viewModel.removePhoto(it) },
        onImagePressed = { openFullSizeImage(it) }
    ) }

    private lateinit var cameraManager: CameraManager

    override fun inflateViewBinding(): ActivityReviewBinding {
        return ActivityReviewBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation? {
        //let the screen orientation change according to the device's orientation
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraManager = CameraManager(this@ReviewActivity) { viewModel.addPhoto(it) }
    }

    override fun ActivityReviewBinding.bind() {

        appBarReview.bind(
            title = "Ulasan",
            onLeftIconPressed = { finish() }
        )

        includeStartsRating.bind(this@ReviewActivity) { viewModel.setRating(it) }

        cvTakePicture.setOnClickListener {
            cameraManager.openCamera()
        }

        btnSend.setOnClickListener {
            viewModel.submitReview(intent)
        }

        val flexboxLayoutManager = FlexboxLayoutManager(root.context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }

        rvPictures.apply {
            adapter = imagesAdapter
            layoutManager = flexboxLayoutManager
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.photos.collect {
                if (it.isNotEmpty()) rvPictures.show() else rvPictures.dispose()
                if (it.size == MAX_IMAGES) cvTakePicture.dispose() else cvTakePicture.show()
                imagesAdapter.submitList(it)
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.isButtonEnabled.collect {
                btnSend.isEnabled = it
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            tietReview.textChanges().collect {
                viewModel.setComment(it.toString())
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect {
                if (it?.isSuccess == true) {
                    val successDialog = buildSuccessDialog(
                        title = "Ulasan Berhasil Ditambahkan",
                        message = "Terima kasih atas ulasan anda!",
                        buttonText = "OK"
                    ) {
                        finish()
                    }
                    successDialog.show()
                }

                if (it?.isError == true) {
                    val errorDialog = buildErrorDialog(
                        title = "Gagal Menambahkan Ulasan",
                        message = "Terjadi kesalahan saat menambahkan ulasan :(, kamu dapat mencoba lagi di halaman riwayat",
                        buttonText = "OK"
                    ) {
                        finish()
                    }
                    errorDialog.show()
                }
            }
        }
    }

    private fun openFullSizeImage(image: Bitmap) {
        val dialog = buildFullSizeImageDialog(image)
        dialog.show()
    }

    override fun systemBarColor(): Int {
        return com.kylix.common.R.color.primary_700
    }

    companion object {
        const val EXTRA_HISTORY_ID = "extra_history_id"
        private const val MAX_IMAGES = 3
    }
}