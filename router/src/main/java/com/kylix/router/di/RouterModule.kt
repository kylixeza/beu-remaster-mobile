package com.kylix.router.di

import com.kylix.auth.navigation.AuthNavigation
import com.kylix.onboard.navigation.OnBoardNavigation
import com.kylix.router.navigator.AuthNavigator
import com.kylix.router.navigator.OnBoardNavigator
import com.kylix.router.navigator.SplashNavigator
import com.kylix.splash.navigation.SplashNavigation
import org.koin.dsl.module

val routerModule = module {
    single<SplashNavigation> { SplashNavigator(get(), get()) }
    single<OnBoardNavigation> { OnBoardNavigator(get()) }
    single<AuthNavigation> { AuthNavigator() }
}