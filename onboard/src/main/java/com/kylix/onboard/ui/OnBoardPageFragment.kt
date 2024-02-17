package com.kylix.onboard.ui

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.kylix.common.base.BaseFragment
import com.kylix.onboard.databinding.FragmentOnBoardPageBinding
import io.dotlottie.loader.DotLottieLoader
import io.dotlottie.loader.models.DotLottie
import io.dotlottie.loader.models.DotLottieResult
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.io.ByteArrayInputStream
import java.io.InputStream

class OnBoardPageFragment : BaseFragment<FragmentOnBoardPageBinding>() {

    override val viewModel by activityViewModel<OnBoardViewModel>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentOnBoardPageBinding {
        return FragmentOnBoardPageBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentOnBoardPageBinding.bind() {

        lifecycleScope.launch {
            viewModel.onBoardContent.collect { content ->
                if (content != null) {
                    tvTitle.text = content.title
                    tvDescription.text = content.description

                    DotLottieLoader.with(requireContext())
                        .fromAsset(content.lottie)
                        .load(object : DotLottieResult {
                            override fun onError(throwable: Throwable) {

                            }

                            override fun onSuccess(result: DotLottie) {
                                val animation = result.animations.entries.first().value
                                val animationInputStream = ByteArrayInputStream(animation)

                                lavOnboard.setAnimation(animationInputStream as InputStream, "animation-${content.lottie}")
                                lavOnboard.playAnimation()
                            }

                        })


                    lavOnboard.setAnimation(content.lottie)
                    lavOnboard.playAnimation()
                }
            }
        }
    }

    companion object {
        fun newInstance() = OnBoardPageFragment()
    }
}