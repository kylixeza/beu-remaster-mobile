package com.kylix.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.kylix.common.R
import com.kylix.common.base.BaseDiffUtil
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.common.databinding.ItemRecipeVerticalBinding
import com.kylix.common.model.RecipeList
import com.kylix.common.util.draw
import com.kylix.common.util.hide
import com.kylix.common.util.show

class RecipeVerticalAdapter(
    private val isFavoriteVisible: Boolean = false,
    private val onItemClicked: (String) -> Unit = {},
    private val onFavoriteClicked: (String) -> Unit = {},
): BaseRecyclerViewAdapter<ItemRecipeVerticalBinding, RecipeList>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemRecipeVerticalBinding {
        return ItemRecipeVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun buildDiffUtilCallback(
        oldList: List<RecipeList>,
        newList: List<RecipeList>
    ): DiffUtil.Callback {
        return RecipeVerticalDiffCallback(oldList, newList)
    }

    override fun ItemRecipeVerticalBinding.bind(item: RecipeList) {

        if (isFavoriteVisible) ivPressedFavorite.show() else ivPressedFavorite.hide()
        tvFoodName.text = item.name
        ivFood.draw(root.context, item.image)
        ivPressedFavorite.setImageResource(
            if (item.isFavorite) R.drawable.ic_favorite else R.drawable.ic_unfavorite_black
        )

        when(item.difficulty) {
            "Mudah" -> {
                cvDifficulty.setCardBackgroundColor(itemView.context.getColor(R.color.success_50))
                tvDifficulty.text = item.difficulty
                tvDifficulty.setTextColor(itemView.context.getColor(R.color.success_900))
            }
            "Menengah" -> {
                cvDifficulty.setCardBackgroundColor(itemView.context.getColor(R.color.primary_50))
                tvDifficulty.text = item.difficulty
                tvDifficulty.setTextColor(itemView.context.getColor(R.color.primary_900))
            }
            "Sulit" -> {
                cvDifficulty.setCardBackgroundColor(itemView.context.getColor(R.color.error_50))
                tvDifficulty.text = item.difficulty
                tvDifficulty.setTextColor(itemView.context.getColor(R.color.error_900))
            }
        }

        tvCookTime.text = "${item.estimationTime} min"
        tvFavoritesCount.text = "${item.favorites} pengguna lainnya"
        tvRating.text = item.rating.toString()

        cvRecipe.setOnClickListener { onItemClicked.invoke(item.recipeId) }

        ivPressedFavorite.setOnClickListener {
            onFavoriteClicked.invoke(item.recipeId)
        }
    }

    inner class RecipeVerticalDiffCallback(
        private val oldList: List<RecipeList>,
        private val newList: List<RecipeList>
    ): BaseDiffUtil<RecipeList, String>(oldList, newList) {
        override fun RecipeList.getItemIdentifier(): String {
            return recipeId
        }

    }
}