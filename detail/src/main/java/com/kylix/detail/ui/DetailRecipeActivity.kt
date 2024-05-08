package com.kylix.detail.ui

import androidx.annotation.OptIn
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.google.android.material.tabs.TabLayoutMediator
import com.kylix.comment.ui.CommentFragment
import com.kylix.common.base.BaseActivity
import com.kylix.common.util.hide
import com.kylix.common.util.show
import com.kylix.common.widget.bind
import com.kylix.detail.adapter.DetailRecipePageAdapter
import com.kylix.detail.navigation.DetailNavigation
import com.kylix.detail.ui.about.AboutFragment
import com.kylix.detail.ui.instruction.InstructionFragment
import com.kylix.detail.ui.review.ReviewFragment
import com.kylix.detail.widget.CustomSneaker
import com.kylix.recipe.R
import com.kylix.recipe.databinding.ActivityDetailRecipeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailRecipeActivity : BaseActivity<ActivityDetailRecipeBinding>() {

    override val viewModel by viewModel<DetailRecipeViewModel>()
    private val navigation by inject<DetailNavigation>()

    private val recipePageAdapter by lazy { DetailRecipePageAdapter(supportFragmentManager, lifecycle) }
    private val customSneaker by lazy { CustomSneaker(this) }

    private lateinit var exoPlayer: ExoPlayer

    override fun inflateViewBinding(): ActivityDetailRecipeBinding {
        return ActivityDetailRecipeBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRecipeDetail(intent)
        //initPlayer()
    }

    @OptIn(UnstableApi::class) override fun ActivityDetailRecipeBinding.bind() {

        val fragments = listOf(
            InstructionFragment.getInstance(),
            AboutFragment.getInstance(),
            ReviewFragment.getInstance()
        )

        val tabTitles = listOf(
            InstructionFragment.getName(),
            AboutFragment.getName(),
            ReviewFragment.getName()
        )

        vpRecipeDetail.adapter = recipePageAdapter

        TabLayoutMediator(tabDetail, vpRecipeDetail) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        recipePageAdapter.submitFragments(fragments)

        bottomBarDetail.apply {
            btnSeeComments.setOnClickListener {
                val recipeId = viewModel.getRecipeId(intent)
                val commentFragment = CommentFragment.newInstance(recipeId)
                commentFragment.show(supportFragmentManager, CommentFragment::class.java.simpleName)
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.recipe.collect {
                if (it != null) {
                    appBarDetail.bind(
                        title = it.name,
                        showRightIcon = true,
                        onLeftIconPressed = { finish() },
                        onRightIconPressed = { viewModel.onFavoriteIconPressed() }
                    )
                    tvVideoSrc.text = it.videoSrc
                    bottomBarDetail.tvCommentCount.text = getString(R.string.comments_count, it.commentsCount)
                    initPlayer()
                }
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.isFavorite.collect {
                appBarDetail.ivFavorite.setImageResource(
                    if (it) com.kylix.common.R.drawable.ic_favorite
                    else com.kylix.common.R.drawable.ic_unfavorite_white
                )
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.elapsedTime.collect {
                if (it.isBlank()) return@collect
                customSneaker.showSuccessSneaker(it) {
                    val historyId = viewModel.historyId.value
                    navigation.navigateToReview(historyId, this@DetailRecipeActivity)
                }
            }
        }
    }

    override fun systemBarColor(): Int {
        return com.kylix.common.R.color.primary_700
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    @OptIn(UnstableApi::class) private fun initPlayer() {
        val url = viewModel.recipe.value?.video ?: return
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        val mediaSource: MediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(url))

        exoPlayer = ExoPlayer.Builder(this).build()
        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.addListener(playbackListener)
        exoPlayer.prepare()
        binding.playerView.player = exoPlayer
    }

    private val playbackListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                ExoPlayer.STATE_READY -> {
                    exoPlayer.playWhenReady = true
                    binding.pbVideoPlayer.hide()
                }
                ExoPlayer.STATE_BUFFERING -> binding.pbVideoPlayer.show()
                ExoPlayer.STATE_ENDED -> viewModel.onStopTimer()
                else -> super.onPlaybackStateChanged(playbackState)
            }
        }
    }

    private fun releasePlayer() {
        exoPlayer.release()
    }

    companion object {
        const val EXTRA_RECIPE_ID = "extra_recipe_id"
    }
}