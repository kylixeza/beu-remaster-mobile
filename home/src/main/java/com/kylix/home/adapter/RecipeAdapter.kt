package com.kylix.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.common.databinding.ItemRecipeHorizontalBinding
import com.kylix.common.model.RecipeList
import com.kylix.common.util.draw
import com.kylix.common.R

class RecipeAdapter(
    private val onRecipeSelected: (String) -> Unit = {}
): BaseRecyclerViewAdapter<ItemRecipeHorizontalBinding, RecipeList>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemRecipeHorizontalBinding {
        return ItemRecipeHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun ItemRecipeHorizontalBinding.bind(item: RecipeList) {
        tvFoodName.text = item.name
        ivFood.draw(root.context, item.image)

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

        root.setOnClickListener { onRecipeSelected.invoke(item.recipeId) }
    }
}