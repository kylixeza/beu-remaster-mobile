package com.kylix.core.di

import androidx.room.Room
import com.kylix.core.BuildConfig
import com.kylix.core.data.api.ApiService
import com.kylix.core.data.api.TokenInterceptor
import com.kylix.core.data.local.db.BeuDatabase
import com.kylix.core.data.local.preference.BeuDataStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory {
        get<BeuDatabase>().beuDao()
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            BeuDatabase::class.java, "beu.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}

val datastoreModule = module {
    single {
        BeuDataStore(androidApplication())
    }
}

val networkModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single { TokenInterceptor(get()) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<TokenInterceptor>())
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .cache(
                okhttp3.Cache(
                    androidContext().cacheDir,
                    10 * 1024 * 1024
                )
            )
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val coreModule = listOf(databaseModule, datastoreModule, networkModule)