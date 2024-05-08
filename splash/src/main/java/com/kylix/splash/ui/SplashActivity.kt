package com.kylix.splash.ui

import androidx.lifecycle.lifecycleScope
import com.kylix.common.base.BaseActivity
import com.kylix.common.util.doNothing
import com.kylix.splash.databinding.ActivitySplashBinding
import com.kylix.splash.navigation.SplashNavigation
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override val viewModel by viewModel<SplashViewModel>()

    private val navigation by inject<SplashNavigation>()

    override fun inflateViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun ActivitySplashBinding.bind() {

        lifecycleScope.launch {
            viewModel.destination.collect {
                when(it) {
                    SplashDestination.ONBOARD -> navigation.navigateToOnBoard(this@SplashActivity)
                    SplashDestination.LOGIN -> navigation.navigateToAuth(this@SplashActivity)
                    SplashDestination.HOME -> navigation.navigateToHome(this@SplashActivity)
                    null -> doNothing()
                }
            }
        }
    }
}