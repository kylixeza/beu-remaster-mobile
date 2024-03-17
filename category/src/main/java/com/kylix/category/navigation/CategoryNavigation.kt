package com.kylix.category.navigation

import android.app.Activity
import android.content.Intent
import com.kylix.category.ui.CategoryActivity

abstract class CategoryNavigation {

    fun navigateItSelf(activity: Activity, categoryId: String, categoryName: String) {
        val intent = Intent(activity, CategoryActivity::class.java)
        intent.putExtra(CategoryActivity.EXTRA_CATEGORY_ID, categoryId)
        intent.putExtra(CategoryActivity.EXTRA_CATEGORY_NAME, categoryName)
        activity.startActivity(intent)
    }

    abstract fun navigateToDetail(activity: Activity, recipeId: String)

}