package com.adorastudios.yawa.data.network_module.retrofit

import com.adorastudios.yawa.data.network_module.response.WeatherResponse
import retrofit2.http.GET

interface WeatherApi {
    @GET("onecall")
    suspend fun get(): WeatherResponse
}
