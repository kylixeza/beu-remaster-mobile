package com.kylix.common.widget

import android.content.Context
import androidx.core.content.ContextCompat
import com.kylix.common.R
import com.kylix.common.databinding.IncludeStarsRatingBinding

fun IncludeStarsRatingBinding.bind(
    context: Context,
    defaultStars: Int = 0,
    customStarSize: Int? = null,
    isClickable: Boolean = true,
    onStarPressed: (Int) -> Unit = {}
) {

    val stars = listOf(
        ivStar1,
        ivStar2,
        ivStar3,
        ivStar4,
        ivStar5
    )

    for (i in 0 until defaultStars) {
        stars[i].setImageDrawable(
            ContextCompat.getDrawable(context, R.drawable.ic_star)
                ?.constantState?.newDrawable()?.constantState?.newDrawable()
        )
    }

    stars.forEachIndexed { index, imageView ->
        imageView.setOnClickListener {
            stars.forEachIndexed { i, star ->
                if (i <= index) {
                    star.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.ic_star)
                            ?.constantState?.newDrawable()?.constantState?.newDrawable()
                    )
                } else {
                    star.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.ic_unstar)
                            ?.constantState?.newDrawable()?.constantState?.newDrawable()
                    )
                }
            }
            onStarPressed(index + 1)
        }
    }

        stars.forEach {
            it.isClickable = isClickable
            if (customStarSize != null) {
                it.layoutParams.height = customStarSize * (context.resources.displayMetrics.density).toInt()
                it.layoutParams.width = customStarSize * (context.resources.displayMetrics.density).toInt()
            }
        }

}
