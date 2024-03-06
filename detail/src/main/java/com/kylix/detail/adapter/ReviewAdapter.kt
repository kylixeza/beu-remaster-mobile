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
import com.kylix.recipe.databinding.ItemReviewBinding

class ReviewAdapter: BaseRecyclerViewAdapter<ItemReviewBinding, Review>() {

    override fun inflateViewBinding(parent: ViewGroup): ItemReviewBinding {
        return ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun ItemReviewBinding.bind(item: Review) {
        tvNameReviewer.text = item.username
        tvRating.text = item.rating.toString()
        ratingBar.rating = item.rating.toFloat()
        ivImageReviewer.draw(root.context, item.avatar)
        tvReview.text = item.comment

        val reviewImageAdapter = ReviewImageAdapter()

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