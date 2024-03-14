package com.kylix.detail.ui.review

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.kylix.common.base.BaseFragment
import com.kylix.common.util.initLinearVertical
import com.kylix.common.util.orZero
import com.kylix.common.widget.buildFullSizeImageDialog
import com.kylix.detail.adapter.ReviewAdapter
import com.kylix.detail.ui.DetailRecipeViewModel
import com.kylix.recipe.R
import com.kylix.recipe.databinding.FragmentReviewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ReviewFragment : BaseFragment<FragmentReviewBinding>() {

    override val viewModel by activityViewModel<DetailRecipeViewModel>()

    private val reviewAdapter by lazy { ReviewAdapter(
        outsidePadding = binding?.rvReview?.paddingStart.orZero() + binding?.rvReview?.paddingEnd.orZero(),
        onImagePressed = { openFullSizeImage(it) }
    ) }

    override fun inflateViewBinding(container: ViewGroup?): FragmentReviewBinding {
        return FragmentReviewBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentReviewBinding.bind() {
        rvReview.initLinearVertical(requireContext(), reviewAdapter)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.recipe.collect {
                it?.apply {
                    reviewAdapter.submitList(reviews)
                }
            }
        }
    }

    private fun openFullSizeImage(image: String) {
        val dialog = requireContext().buildFullSizeImageDialog(image)
        dialog.show()
    }

    companion object {
        fun getInstance(): ReviewFragment {
            return ReviewFragment()
        }

        fun getName(): String {
            return "Ulasan"
        }
    }

}