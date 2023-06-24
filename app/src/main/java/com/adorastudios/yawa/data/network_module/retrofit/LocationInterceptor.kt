package com.adorastudios.yawa.data.network_module.retrofit

import com.adorastudios.yawa.data.network_module.preferences.LocationPreferences
import okhttp3.Interceptor
import okhttp3.Response

class LocationInterceptor(private val locationPreferences: LocationPreferences): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val curr = locationPreferences.isUseCurrentLocation()

        val origin = chain.request()
        val urlBuilder = origin.url.newBuilder()
        val url = if (curr) {
            val currLat = locationPreferences.getLatitude()
            val currLon = locationPreferences.getLongitude()
            urlBuilder
                .addQueryParameter("lat", "" + currLat)
                .addQueryParameter("lon", "" + currLon)
                .build()
        } else {
            val lat = locationPreferences.getLatitude()
            val lon = locationPreferences.getLongitude()
            urlBuilder
                .addQueryParameter("lat", "" + lat)
                .addQueryParameter("lon", "" + lon)
                .build()
        }

        val requestBuilder = origin.newBuilder().url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
