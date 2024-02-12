package com.kylix.core.data.api

import com.kylix.core.data.local.preference.BeuDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val dataStore: BeuDataStore
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { dataStore.getToken() }

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }

}