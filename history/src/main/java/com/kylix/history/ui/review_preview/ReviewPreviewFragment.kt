package com.kylix.history.ui.review_preview

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.kylix.common.adapter.ImagesAdapter
import com.kylix.common.base.BaseBottomSheetDialogFragment
import com.kylix.common.util.draw
import com.kylix.common.widget.bind
import com.kylix.common.widget.buildFullSizeImageDialog
import com.kylix.history.databinding.FragmentReviewPreviewBinding
import com.kylix.history.util.toGMT8Format
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewPreviewFragment : BaseBottomSheetDialogFragment<FragmentReviewPreviewBinding>() {

    override val viewModel by viewModel<ReviewPreviewViewModel>()

    private val imagesAdapter by lazy { ImagesAdapter<String>(
        outsidePadding = binding?.run {
            val rootHorizontalPadding = root.paddingStart + root.paddingEnd
            val innerCardPadding = includeReviewPreview.clReview.paddingStart + includeReviewPreview.clReview.paddingEnd
            rootHorizontalPadding + innerCardPadding
        } ?: 0
    ) { openFullSizeImage(it) }
    }

    override fun inflateViewBinding(container: ViewGroup?): FragmentReviewPreviewBinding {
        return FragmentReviewPreviewBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentReviewPreviewBinding.bind() {
        viewModel.getReview(requireArguments())

        includeReviewPreview.rvPictures.apply {
            val flexboxLayoutManager = FlexboxLayoutManager(root.context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.FLEX_START
            }
            layoutManager = flexboxLayoutManager
            adapter = imagesAdapter
        }

        btnClose.setOnClickListener { dismiss() }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.review.collect {
                if (it == null) return@collect

                ivAvatar.draw(requireContext(), it.avatar) {
                    placeholder(com.kylix.common.R.drawable.ilu_default_profile_picture)
                        .circleCrop()
                }
                tvUsername.text = it.username
                tvTimeStamp.text = it.timeStamp.toGMT8Format()

                includeReviewPreview.apply {
                    starsRating.bind(
                        context = requireContext(),
                        defaultStars = it.rating,
                        isClickable = false
                    )
                    tvReview.text = it.comment
                    imagesAdapter.submitList(it.images)
                }
            }
        }
    }

    private fun openFullSizeImage(image: String) {
        val dialog = requireContext().buildFullSizeImageDialog(image)
        dialog.show()
    }

    companion object {
        const val EXTRA_HISTORY_ID = "extra_history_id"

        fun newInstance(historyId: String): ReviewPreviewFragment {
            val fragment = ReviewPreviewFragment()
            fragment.arguments = Bundle().apply {
                putString(EXTRA_HISTORY_ID, historyId)
            }
            return fragment
        }
    }
}