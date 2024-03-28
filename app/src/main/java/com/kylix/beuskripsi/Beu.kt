package com.kylix.beuskripsi

import android.app.Application
import com.kylix.auth.di.authModule
import com.kylix.category.di.categoryModule
import com.kylix.reset_password.di.resetPasswordModule
import com.kylix.change_profile.di.changeProfileModule
import com.kylix.core.di.databaseModule
import com.kylix.core.di.datastoreModule
import com.kylix.core.di.networkModule
import com.kylix.core.di.repositoryModule
import com.kylix.detail.di.detailModule
import com.kylix.favorite.di.favoriteModule
import com.kylix.help_center.di.helpCenterModule
import com.kylix.history.di.historyModule
import com.kylix.home.di.homeModule
import com.kylix.onboard.di.onBoardModule
import com.kylix.profile.di.profileModule
import com.kylix.review.di.reviewModule
import com.kylix.router.di.routerModule
import com.kylix.search.di.searchModule
import com.kylix.splash.di.splashModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Beu: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@Beu)
            modules(
                databaseModule,
                datastoreModule,
                networkModule,
                repositoryModule,
                routerModule,
                splashModule,
                onBoardModule,
                authModule,
                homeModule,
                detailModule,
                reviewModule,
                categoryModule,
                searchModule,
                profileModule,
                changeProfileModule,
                resetPasswordModule,
                favoriteModule,
                historyModule,
                helpCenterModule,
            )
        }
    }
}