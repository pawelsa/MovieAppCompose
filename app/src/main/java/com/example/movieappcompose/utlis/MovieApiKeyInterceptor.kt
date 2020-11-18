package com.example.movieappcompose.utlis

import com.example.movieappcompose.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class MovieApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val request = request().newBuilder()
        val originalUrl = request().url()
        val url = originalUrl
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()
        request.url(url)
        proceed(request.build())
    }
}