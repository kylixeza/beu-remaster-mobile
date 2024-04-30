package com.kylix.favorite.ui

import com.kylix.common.adapter.RecipeVerticalAdapter
import com.kylix.common.base.BaseActivity
import com.kylix.common.util.hide
import com.kylix.common.util.initLinearVertical
import com.kylix.common.util.show
import com.kylix.common.widget.bind
import com.kylix.favorite.R
import com.kylix.favorite.databinding.ActivityFavoriteBinding
import com.kylix.favorite.navigation.FavoriteNavigation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : BaseActivity<ActivityFavoriteBinding>() {

    override val viewModel by viewModel<FavoriteViewModel>()

    private val favoriteNavigation by inject<FavoriteNavigation>()

    private val favoriteAdapter by lazy {
        RecipeVerticalAdapter(
            isFavoriteVisible = true,
            onItemClicked = { recipeId ->
                favoriteNavigation.navigateToDetail(this, recipeId)
            },
            onFavoriteClicked = { recipeId ->
                viewModel.onFavoritePressed(recipeId)
            }
        )
    }

    override fun inflateViewBinding(): ActivityFavoriteBinding {
        return ActivityFavoriteBinding.inflate(layoutInflater)
    }

    override fun ActivityFavoriteBinding.bind() {
        viewModel.getFavorites()
        favoriteAppBar.bind(
            title = "Favorit",
            onLeftIconPressed = { finish() }
        )

        rvFavorite.initLinearVertical(this@FavoriteActivity, favoriteAdapter)
    }

    override fun observeState() {
        super.observeState()

        binding.apply {
            viewModel.favorites.observe {
                if (it.isEmpty()) tvEmptyFavorite.show() else tvEmptyFavorite.hide()
                favoriteAdapter.submitList(it)
            }
        }
    }

    override fun systemBarColor(): Int? {
        return com.kylix.common.R.color.primary_700
    }
}