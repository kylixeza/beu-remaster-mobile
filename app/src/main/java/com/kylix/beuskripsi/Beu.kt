package com.kylix.beuskripsi

import android.app.Application
import com.kylix.auth.di.authModule
import com.kylix.core.di.databaseModule
import com.kylix.core.di.datastoreModule
import com.kylix.core.di.networkModule
import com.kylix.core.di.repositoryModule
import com.kylix.onboard.di.onBoardModule
import com.kylix.router.di.routerModule
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
                authModule
            )
        }
    }
}