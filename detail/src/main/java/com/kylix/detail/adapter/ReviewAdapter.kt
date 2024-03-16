package com.kylix.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.common.model.Review
import com.kylix.common.util.draw
import com.kylix.common.widget.bind
import com.kylix.recipe.databinding.ItemReviewBinding

class ReviewAdapter(
    private val outsidePadding: Int = 0,
    private val onImagePressed: (String) -> Unit = {}
): BaseRecyclerViewAdapter<ItemReviewBinding, Review>() {

    override fun inflateViewBinding(parent: ViewGroup): ItemReviewBinding {
        return ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun ItemReviewBinding.bind(item: Review) {
        tvNameReviewer.text = item.username
        tvRating.text = item.rating.toString()
        ratingBar.bind(
            root.context,
            customStarSize = 24,
            defaultStars = item.rating,
            isClickable = false
        )
        ivImageReviewer.draw(root.context, item.avatar) {
            placeholder(com.kylix.common.R.drawable.ilu_default_profile_picture)
                .circleCrop()
        }
        tvReview.text = item.comment

        val reviewImageAdapter = ReviewImageAdapter(
            outsidePadding = outsidePadding,
            onImagePressed = { onImagePressed(it) }
        )

        val flexboxLayoutManager = FlexboxLayoutManager(root.context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }

        rvReviewImages.apply {
            layoutManager = flexboxLayoutManager
            adapter = reviewImageAdapter
        }

        reviewImageAdapter.submitList(item.images)
    }
}