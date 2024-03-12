package com.kylix.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.common.util.draw
import com.kylix.recipe.databinding.ItemReviewImagesBinding
import kotlin.math.roundToInt

class ReviewImageAdapter(
    private val outsidePadding: Int = 0,
    private val onImagePressed: (String) -> Unit = {}
): BaseRecyclerViewAdapter<ItemReviewImagesBinding, String>() {

    override fun inflateViewBinding(parent: ViewGroup): ItemReviewImagesBinding {
        return ItemReviewImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun ItemReviewImagesBinding.bind(item: String) {
        val screenWidthDp = (root.context.resources.configuration.screenWidthDp * root.context.resources.displayMetrics.density).toInt() - outsidePadding
        val squareRatio = 0.95
        val marginRatio = 1 - squareRatio

        val squareSize = (screenWidthDp * squareRatio)
        val marginSize = (screenWidthDp * marginRatio)

        val itemsInOneRow = 3
        val marginsInOneRow = itemsInOneRow * 2

        cvReviewImage.layoutParams.apply {
            width = (squareSize.roundToInt() / itemsInOneRow)
            height = (squareSize.roundToInt() / itemsInOneRow)
            if (this is ViewGroup.MarginLayoutParams) {
                marginStart = marginSize.roundToInt() / marginsInOneRow
                marginEnd = marginSize.roundToInt() / marginsInOneRow
            }
        }

        root.setOnClickListener { onImagePressed(item) }

        ivReview.draw(root.context, item) { optionalCenterCrop() }
    }
}