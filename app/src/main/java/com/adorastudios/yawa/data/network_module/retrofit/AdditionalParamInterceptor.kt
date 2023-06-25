package com.adorastudios.yawa.data.network_module.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class AdditionalParamInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val urlBuilder = origin.url.newBuilder()
        val url = urlBuilder
            .addQueryParameter("exclude", "minutely,alerts")
            .addQueryParameter("units", "metric")
            .addQueryParameter("lang", "en")
            .build()
        val requestBuilder = origin.newBuilder().url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
