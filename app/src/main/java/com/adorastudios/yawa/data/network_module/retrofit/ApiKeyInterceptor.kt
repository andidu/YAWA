package com.adorastudios.yawa.data.network_module.retrofit

import com.adorastudios.yawa.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val urlBuilder = origin.url.newBuilder()
        val url = urlBuilder.addQueryParameter("appid", BuildConfig.WEATHER_API_KEY).build()

        val requestBuilder = origin.newBuilder().url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
