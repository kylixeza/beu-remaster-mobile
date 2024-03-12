package com.kylix.review.widget

import android.content.Context
import androidx.core.content.ContextCompat
import com.kylix.review.R
import com.kylix.review.databinding.IncludeStarsRatingBinding

fun IncludeStarsRatingBinding.bind(
    context: Context,
    defaultStars: Int = 0,
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
}
