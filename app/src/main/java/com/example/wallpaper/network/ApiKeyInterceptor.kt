package com.example.wallpaper.network

import com.example.wallpaper.utils.API_KEY
import com.example.wallpaper.utils.CLIENT_ID
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val httpUrl = request.url()
            .newBuilder()
            .addQueryParameter(CLIENT_ID, API_KEY)
            .build()

        request = request
            .newBuilder()
            .url(httpUrl)
            .build()

        return chain.proceed(request)
    }
}