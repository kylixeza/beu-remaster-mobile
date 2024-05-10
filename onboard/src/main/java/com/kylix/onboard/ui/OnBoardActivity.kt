package com.kylix.onboard.ui

import androidx.lifecycle.lifecycleScope
import com.kylix.common.base.BaseActivity
import com.kylix.onboard.adapter.OnBoardPageAdapter
import com.kylix.onboard.databinding.ActivityOnBoardBinding
import com.kylix.onboard.navigation.OnBoardNavigation
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.viewpager2.pageSelections

class OnBoardActivity : BaseActivity<ActivityOnBoardBinding>() {

    override val viewModel by viewModel<OnBoardViewModel>()

    private val navigation by inject<OnBoardNavigation>()

    private val onBoardPageAdapter: OnBoardPageAdapter by lazy { OnBoardPageAdapter(supportFragmentManager, lifecycle) }

    override fun inflateViewBinding(): ActivityOnBoardBinding {
        return ActivityOnBoardBinding.inflate(layoutInflater)
    }

    override fun ActivityOnBoardBinding.bind() {

        supportActionBar?.hide()

        val fragments = listOf(
            OnBoardPageFragment.newInstance(),
            OnBoardPageFragment.newInstance(),
            OnBoardPageFragment.newInstance()
        )


        onBoardPageAdapter.setAllFragments(fragments)
        viewPager.adapter = onBoardPageAdapter
        viewPager.offscreenPageLimit = 1
        pageIndicatorView.attachTo(viewPager)

        btnNext.setOnClickListener {
            if (viewModel.isLastPage.value) {
                viewModel.passOnBoard()
                navigation.navigateToAuth(this@OnBoardActivity)
            }
            else
                viewModel.nextPage()
        }

        btnSkip.setOnClickListener {
            viewModel.passOnBoard()
            navigation.navigateToAuth(this@OnBoardActivity)
        }


        viewPager.pageSelections().skipInitialValue().onEach { viewModel.setPage(it.plus(1)) }
            .launchIn(lifecycleScope)

        lifecycleScope.launch {
            viewModel.page.collect { page ->
                binding.viewPager.setCurrentItem(page.minus(1), true)
            }
        }
    }
}