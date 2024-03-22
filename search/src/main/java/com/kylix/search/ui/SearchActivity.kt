package com.kylix.search.ui

import com.kylix.common.adapter.RecipeVerticalAdapter
import com.kylix.common.base.BaseActivity
import com.kylix.common.base.BaseViewModel
import com.kylix.common.util.dispose
import com.kylix.common.util.hide
import com.kylix.common.util.initLinearVertical
import com.kylix.common.util.show
import com.kylix.common.widget.bind
import com.kylix.common.widget.errorSnackbar
import com.kylix.search.R
import com.kylix.search.databinding.ActivitySearchBinding
import com.kylix.search.navigation.SearchNavigation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    override val viewModel by viewModel<SearchViewModel>()

    private val searchNavigation by inject<SearchNavigation>()

    private val searchAdapter by lazy {
        RecipeVerticalAdapter(
            isFavoriteVisible = true,
            onItemClicked = { searchNavigation.navigateToDetail(this, it) },
        )
    }

    override fun inflateViewBinding(): ActivitySearchBinding {
        return ActivitySearchBinding.inflate(layoutInflater)
    }

    override fun ActivitySearchBinding.bind() {
        searchAppBar.bind(
            this@SearchActivity,
            title = getString(R.string.search_title),
            showSearchView = true,
            searchViewHint = resources.getString(R.string.query_hint),
            onBack = { finish() },
            onSearch = { viewModel.setQuery(it) }
        )

        rvSearch.initLinearVertical(this@SearchActivity, searchAdapter)
    }

    override fun observeState() {
        binding.apply {
            viewModel.uiState.observe {
                if (it == null) return@observe
                if (it.isLoading) pbSearch.show() else pbSearch.hide()
                if (it.isLoading) rvSearch.hide() else rvSearch.show()
                if (it.isError) root.errorSnackbar(it.errorMessage)
            }

            viewModel.recipes.observe {
                if (it == null) tvNoResult.hide(); searchAdapter.submitList(emptyList())
                if (it?.isEmpty() == true) tvNoResult.show() else tvNoResult.hide()
                searchAdapter.submitList(it.orEmpty())
            }

            viewModel.query.observe {
                tvNoResult.text = getString(R.string.no_result, it)
            }
        }
    }

    override fun systemBarColor(): Int {
        return com.kylix.common.R.color.primary_700
    }
}