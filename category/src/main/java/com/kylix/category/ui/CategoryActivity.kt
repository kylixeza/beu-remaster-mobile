package com.kylix.category.ui

import androidx.lifecycle.lifecycleScope
import com.kylix.category.R
import com.kylix.category.databinding.ActivityCategoryBinding
import com.kylix.category.navigation.CategoryNavigation
import com.kylix.common.adapter.RecipeVerticalAdapter
import com.kylix.common.base.BaseActivity
import com.kylix.common.util.initLinearVertical
import com.kylix.common.widget.bind
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryActivity : BaseActivity<ActivityCategoryBinding>() {

    override val viewModel by viewModel<CategoryViewModel>()

    private val categoryNavigation by inject<CategoryNavigation>()

    private val categoryAdapter by lazy { RecipeVerticalAdapter(
        onItemClicked = { recipeId -> categoryNavigation.navigateToDetail(this, recipeId) }
    ) }

    override fun inflateViewBinding(): ActivityCategoryBinding {
        return ActivityCategoryBinding.inflate(layoutInflater)
    }

    override fun ActivityCategoryBinding.bind() {
        viewModel.getRecipes(intent)
        viewModel.getCategoryName(intent)

        rvCategory.initLinearVertical(this@CategoryActivity, categoryAdapter)

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.recipes.collect {
                categoryAdapter.submitList(it)
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.categoryName.collect {
                categoryAppBar.bind(
                    it,
                    onLeftIconPressed = { finish() }
                )
            }
        }
    }

    override fun systemBarColor(): Int {
        return com.kylix.common.R.color.primary_700
    }

    companion object {
        const val EXTRA_CATEGORY_NAME = "extra_category_name"
        const val EXTRA_CATEGORY_ID = "extra_category_id"
    }
}