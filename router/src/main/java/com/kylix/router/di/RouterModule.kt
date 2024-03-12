package com.kylix.router.di

import com.kylix.auth.navigation.AuthNavigation
import com.kylix.detail.navigation.DetailNavigation
import com.kylix.home.navigation.HomeNavigation
import com.kylix.main.navigation.MainNavigation
import com.kylix.onboard.navigation.OnBoardNavigation
import com.kylix.review.navigation.ReviewNavigation
import com.kylix.router.navigator.AuthNavigator
import com.kylix.router.navigator.DetailNavigator
import com.kylix.router.navigator.HomeNavigator
import com.kylix.router.navigator.MainNavigator
import com.kylix.router.navigator.OnBoardNavigator
import com.kylix.router.navigator.ReviewNavigator
import com.kylix.router.navigator.SplashNavigator
import com.kylix.splash.navigation.SplashNavigation
import org.koin.dsl.module

val routerModule = module {
    single<SplashNavigation> { SplashNavigator(get(), get(), get()) }
    single<OnBoardNavigation> { OnBoardNavigator(get()) }
    single<AuthNavigation> { AuthNavigator(get()) }
    single<MainNavigation> { MainNavigator() }
    single<HomeNavigation> { HomeNavigator(get()) }
    single<DetailNavigation> { DetailNavigator(get()) }
    single<ReviewNavigation> { ReviewNavigator() }
}