package com.kylix.detail.ui.instruction

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.kylix.common.base.BaseFragment
import com.kylix.common.base.BaseViewModel
import com.kylix.common.util.initGridVertical
import com.kylix.common.util.initLinearVertical
import com.kylix.detail.adapter.IngredientToolAdapter
import com.kylix.detail.adapter.StepAdapter
import com.kylix.detail.ui.DetailRecipeViewModel
import com.kylix.recipe.databinding.FragmentInstructionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class InstructionFragment : BaseFragment<FragmentInstructionBinding>() {

    private val ingredientAdapter by lazy { IngredientToolAdapter() }
    private val toolAdapter by lazy { IngredientToolAdapter() }
    private val stepAdapter by lazy { StepAdapter() }

    override val viewModel by activityViewModel<DetailRecipeViewModel>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentInstructionBinding {
        return FragmentInstructionBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentInstructionBinding.bind() {
        rvIngredients.initGridVertical(requireContext(), ingredientAdapter, 2)
        rvTools.initGridVertical(requireContext(), toolAdapter, 2)
        rvSteps.initLinearVertical(requireContext(), stepAdapter)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.recipe.collect { recipe ->
                recipe?.apply {
                    ingredientAdapter.submitList(ingredients)
                    toolAdapter.submitList(tools)
                    stepAdapter.submitList(steps)
                }
            }
        }
    }

    companion object {
        fun getInstance(): InstructionFragment {
            return InstructionFragment()
        }

        fun getName(): String {
            return "Instruction"
        }
    }

}