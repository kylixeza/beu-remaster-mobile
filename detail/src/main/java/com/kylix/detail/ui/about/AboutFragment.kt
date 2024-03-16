package com.kylix.detail.ui.about

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.kylix.common.base.BaseFragment
import com.kylix.common.util.initLinearVertical
import com.kylix.common.widget.bind
import com.kylix.detail.adapter.NutritionAdapter
import com.kylix.detail.ui.DetailRecipeViewModel
import com.kylix.recipe.R
import com.kylix.recipe.databinding.FragmentAboutBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import kotlin.math.roundToInt

class AboutFragment : BaseFragment<FragmentAboutBinding>() {

    override val viewModel by activityViewModel<DetailRecipeViewModel>()

    private val nutritionAdapter by lazy { NutritionAdapter() }

    override fun inflateViewBinding(container: ViewGroup?): FragmentAboutBinding {
        return FragmentAboutBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentAboutBinding.bind() {
        rvNutritionInformation.initLinearVertical(requireContext(), nutritionAdapter)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.recipe.collect {
                if (it != null) {

                    tvNumberOfRating.text = getString(R.string.rated_by, it.averageCount)
                    tvAverageRating.text = it.averageRating.toString()
                    ratingBar.bind(
                        requireContext(),
                        customStarSize = 24,
                        defaultStars = it.averageRating.roundToInt(),
                        isClickable = false,
                    )

                    tvDescription.text = it.description
                    tvEstimateTime.text = it.estimateTime
                    nutritionAdapter.submitList(it.nutritionInformation)
                }
            }
        }
    }

    companion object {
        fun getInstance(): AboutFragment {
            return AboutFragment()
        }

        fun getName(): String {
            return "Tentang"
        }
    }

}