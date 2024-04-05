package com.kylix.common.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.kylix.common.base.BaseDiffUtil
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.common.databinding.ItemImagesBinding
import com.kylix.common.util.draw
import com.kylix.common.util.hide
import com.kylix.common.util.show
import kotlin.math.roundToInt

open class ImagesAdapter<ImageDataType>(
    private val outsidePadding: Int = 0,
    private val onImageDeleted: ((ImageDataType) -> Unit?)? = null,
    private val onImagePressed: (ImageDataType) -> Unit = {}
): BaseRecyclerViewAdapter<ItemImagesBinding, ImageDataType>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemImagesBinding {
        return ItemImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun buildDiffUtilCallback(
        oldList: List<ImageDataType>,
        newList: List<ImageDataType>
    ): DiffUtil.Callback? {
        return ImageDiffUtilCallback(oldList, newList)
    }

    override fun ItemImagesBinding.bindWithPosition(item: ImageDataType, position: Int) {

        calculateImageSize()

        if (onImageDeleted != null) ivDelete.show() else ivDelete.hide()
        ivReview.draw(
            root.context,
            if (item is Bitmap) item else item.toString()
        ) { optionalCenterCrop() }
        ivDelete.setOnClickListener { onImageDeleted?.invoke(item) }

        root.setOnClickListener { onImagePressed(item) }
    }

    inner class ImageDiffUtilCallback(
        oldItems: List<ImageDataType>,
        newItems: List<ImageDataType>,
    ): BaseDiffUtil<ImageDataType, Int>(oldItems, newItems){
        override fun ImageDataType.getItemIdentifier(): Int {
            return when(this) {
                is Bitmap -> this.generationId
                else -> this.hashCode()
            }
        }
    }

    open fun ItemImagesBinding.calculateImageSize() {
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
    }
}