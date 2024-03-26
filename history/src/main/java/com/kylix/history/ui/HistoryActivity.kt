package com.kylix.history.ui

import com.kylix.common.base.BaseActivity
import com.kylix.common.util.initLinearVertical
import com.kylix.common.widget.bind
import com.kylix.history.adapter.HistoryAdapter
import com.kylix.history.databinding.ActivityHistoryBinding
import com.kylix.history.navigation.HistoryNavigation
import com.kylix.history.ui.review_preview.ReviewPreviewFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : BaseActivity<ActivityHistoryBinding>() {

    override val viewModel by viewModel<HistoryViewModel>()
    private val historyNavigation by inject<HistoryNavigation>()

    private val historyAdapter by lazy { HistoryAdapter(
        onReviewClicked = { isReviewed, historyId -> onReviewClicked(isReviewed, historyId) }
    ) }

    override fun inflateViewBinding(): ActivityHistoryBinding {
        return ActivityHistoryBinding.inflate(layoutInflater)
    }

    override fun ActivityHistoryBinding.bind() {
        viewModel.getHistories()

        historyAppBar.bind(
            title = "Riwayat",
            onLeftIconPressed = { finish() }
        )
        rvHistory.initLinearVertical(this@HistoryActivity, historyAdapter)
    }

    override fun systemBarColor(): Int {
        return com.kylix.common.R.color.primary_700
    }

    override fun observeState() {
        super.observeState()

        viewModel.histories.observe {
            historyAdapter.submitList(it)
        }
    }

    private fun onReviewClicked(isReviewed: Boolean, historyId: String) {
        if (!isReviewed) {
            historyNavigation.navigateToReview(this, historyId)
        } else {
            val reviewFragment = ReviewPreviewFragment.newInstance(historyId)
            reviewFragment.show(supportFragmentManager, ReviewPreviewFragment::class.java.simpleName)
        }
    }
}