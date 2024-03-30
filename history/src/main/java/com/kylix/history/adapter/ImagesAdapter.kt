package com.kylix.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.common.util.dispose
import com.kylix.common.util.draw
import com.kylix.history.databinding.ItemListImagesBinding
import kotlin.math.roundToInt

class ImagesAdapter(
    private val outsidePadding: Int = 0,
    private val onImagePressed: (String) -> Unit = {}
): BaseRecyclerViewAdapter<ItemListImagesBinding, String>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemListImagesBinding {
        return ItemListImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun ItemListImagesBinding.bindWithPosition(item: String, position: Int) {

        val density = root.context.resources.displayMetrics.density
        val screenWidthDp =
            (root.context.resources.configuration.screenWidthDp * density) - (outsidePadding)
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
        ivReview.draw(root.context, item) { optionalCenterCrop() }

        root.setOnClickListener { onImagePressed(item) }
    }
}