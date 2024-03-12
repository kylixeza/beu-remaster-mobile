package com.kylix.review.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.kylix.common.base.BaseDiffUtil
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.common.util.draw
import com.kylix.review.databinding.ItemListImagesBinding
import kotlin.math.roundToInt

class ImagesAdapter(
    private val outsidePadding: Int = 0,
    private val onImageDeleted: (Bitmap) -> Unit = {},
    private val onImagePressed: (Bitmap) -> Unit = {}
): BaseRecyclerViewAdapter<ItemListImagesBinding, Bitmap>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemListImagesBinding {
        return ItemListImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun buildDiffUtilCallback(
        oldList: List<Bitmap>,
        newList: List<Bitmap>
    ): DiffUtil.Callback {
        return ImageDiffUtilCallback(oldList, newList)
    }

    override fun ItemListImagesBinding.bindWithPosition(item: Bitmap, position: Int) {

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
        ivReview.draw(root.context, item) { optionalCenterCrop() }
        ivDelete.setOnClickListener { onImageDeleted(item) }

        root.setOnClickListener { onImagePressed(item) }
    }

    inner class ImageDiffUtilCallback(
        oldItems: List<Bitmap>,
        newItems: List<Bitmap>,
    ): BaseDiffUtil<Bitmap, Int>(oldItems, newItems){
        override fun Bitmap.getItemIdentifier(): Int {
            return this.generationId
        }

    }
}