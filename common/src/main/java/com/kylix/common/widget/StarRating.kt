package com.kylix.common.widget

import android.content.Context
import androidx.core.content.ContextCompat
import com.kylix.common.R
import com.kylix.common.databinding.IncludeStarsRatingBinding

fun IncludeStarsRatingBinding.bind(
    context: Context,
    defaultStars: Int = 0,
    starSize: Int = ivStar1.height,
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
        it.layoutParams.height = starSize * (context.resources.displayMetrics.density).toInt()
        it.layoutParams.width = starSize * (context.resources.displayMetrics.density).toInt()
    }
}
