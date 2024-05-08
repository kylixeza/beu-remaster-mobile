package com.kylix.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.kylix.common.R
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.common.model.History
import com.kylix.common.util.dispose
import com.kylix.common.util.draw
import com.kylix.common.widget.bind
import com.kylix.common.widget.reset
import com.kylix.history.databinding.ItemHistoryBinding

class HistoryAdapter(
    private val onReviewClicked: (Boolean, String) -> Unit = { _, _ -> },
): BaseRecyclerViewAdapter<ItemHistoryBinding, History>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemHistoryBinding {
        return ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun ItemHistoryBinding.bindWithPosition(item: History, position: Int) {
        cvReview.starsRating.reset(root.context)

        tvFoodName.text = item.recipeName
        tvTimestamp.text = item.timeStamp
        tvTimeSpent.text = item.spendTime
        ivFood.draw(root.context, item.recipeImage) {
           transform(RoundedCorners(16))
        }

        cvIsReviewed.setCardBackgroundColor(
            if (item.isReviewed) itemView.context.getColor(R.color.success_50)
            else itemView.context.getColor(R.color.error_50)
        )
        tvIsReviewed.apply {
            text = if (item.isReviewed) "Sudah diulas" else "Belum diulas"
            setTextColor(
                if (item.isReviewed) itemView.context.getColor(R.color.success_900)
                else itemView.context.getColor(R.color.error_900)
            )
        }

        cvReview.starsRating.bind(
            context = root.context,
            defaultStars = item.reviewRating,
            customStarSize = 24,
            isClickable = false,
        )

        cvReview.cvReview.setOnClickListener {
            onReviewClicked(item.isReviewed, item.historyId)
        }

        if (position == itemList.size - 1) vDivider.dispose()
    }
}