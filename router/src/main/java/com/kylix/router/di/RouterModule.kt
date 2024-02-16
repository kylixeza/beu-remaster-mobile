package com.kylix.router.di

import com.kylix.onboard.navigation.OnBoardNavigation
import com.kylix.router.navigator.OnBoardNavigator
import com.kylix.router.navigator.SplashNavigator
import com.kylix.splash.navigation.SplashNavigation
import org.koin.dsl.module

val routerModule = module {
    single<SplashNavigation> { SplashNavigator() }
    single<OnBoardNavigation> { OnBoardNavigator() }
}