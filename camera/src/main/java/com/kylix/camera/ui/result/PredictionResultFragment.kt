package com.kylix.camera.ui.result

import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.kylix.camera.databinding.FragmentPredictionResultBinding
import com.kylix.camera.navigation.CameraNavigation
import com.kylix.camera.ui.CameraViewModel
import com.kylix.camera.widget.buildPredictionSendDialog
import com.kylix.common.adapter.RecipeVerticalAdapter
import com.kylix.common.base.BaseBottomSheetDialogFragment
import com.kylix.common.util.initLinearVertical
import com.kylix.common.util.show
import com.kylix.core.BuildConfig
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PredictionResultFragment(
    private val actual: String,
    private val probability: Float,
    private val imageBitmap: Bitmap?
) : BaseBottomSheetDialogFragment<FragmentPredictionResultBinding>() {

    override val viewModel by viewModel<PredictionResultViewModel>()

    private val predictionResultNavigation by inject<CameraNavigation>()

    private val recipeAdapter by lazy { RecipeVerticalAdapter(
        isFavoriteVisible = true,
        onItemClicked = { predictionResultNavigation.navigateToDetail(requireActivity(), it) },
        onFavoriteClicked = { viewModel.onFavoritePressed(it) }
    ) }

    private val isDebugMode = BuildConfig.DEBUG

    override fun inflateViewBinding(container: ViewGroup?): FragmentPredictionResultBinding {
        return FragmentPredictionResultBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentPredictionResultBinding.bind() {
        rvPredictionResult.apply {
            initLinearVertical(requireContext(), recipeAdapter)

            val screenHeightDp = (resources.configuration.screenHeightDp * resources.displayMetrics.density).toInt()
            val heightScale = 0.55
            layoutParams.apply {
                height = (screenHeightDp * heightScale).toInt()
            }
        }
        if (isDebugMode) btnSendPrediction.show()
        viewModel.getRelatedRecipes(when(actual) {
            "Fried Rice" -> "Nasi Goreng"
            else -> actual
        })

        btnSendPrediction.setOnClickListener {
            requireContext().buildPredictionSendDialog(
                actual, probability.toDouble(), imageBitmap
            ) {
                viewModel.postPredictionResult(it, actual, probability.toDouble(), imageBitmap ?: return@buildPredictionSendDialog) {
                    this.dismiss()
                    this@PredictionResultFragment.dismiss()
                }
            }.show()
        }

        tvPredictionResult.text = when(actual) {
            "Fried Rice" -> "Nasi Goreng"
            else -> actual
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipes.collect {
                if (it.isEmpty()) return@collect
                recipeAdapter.submitList(it)
            }
        }
    }
}