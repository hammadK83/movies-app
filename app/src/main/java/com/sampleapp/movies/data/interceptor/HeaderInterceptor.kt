package com.sampleapp.movies.data.interceptor

import com.sampleapp.movies.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

private const val HEADER_AUTH_TOKEN = "Authorization"
private const val HEADER_AUTH_TOKEN_PREFIX = "Bearer"

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder().apply {
            addHeader(
                HEADER_AUTH_TOKEN,
                "$HEADER_AUTH_TOKEN_PREFIX ${BuildConfig.TMDB_ACCESS_TOKEN}")
        }
        return chain.proceed(builder.build())
    }
}