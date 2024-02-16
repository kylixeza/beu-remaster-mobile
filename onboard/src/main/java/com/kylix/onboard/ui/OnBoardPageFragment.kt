package com.kylix.onboard.ui

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.kylix.common.base.BaseFragment
import com.kylix.common.base.BaseViewModel
import com.kylix.onboard.databinding.FragmentOnBoardPageBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardPageFragment : BaseFragment<FragmentOnBoardPageBinding>() {

    override val viewModel by viewModel<OnBoardViewModel>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentOnBoardPageBinding {
        return FragmentOnBoardPageBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentOnBoardPageBinding.bind() {

        lifecycleScope.launch {
            viewModel.onBoardContent.collect { content ->
                if (content != null) {
                    tvTitle.text = content.title
                    tvDescription.text = content.description
                    lavOnboard.setAnimation(content.image)
                    lavOnboard.playAnimation()
                }
            }
        }

    }
}