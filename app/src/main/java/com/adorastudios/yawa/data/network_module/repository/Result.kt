package com.adorastudios.yawa.data.network_module.repository

import com.adorastudios.yawa.data.network_module.response.WeatherResponse

sealed class Result {
    data class OK(val weatherResponse: WeatherResponse): Result()
    data class Error(val error: String?): Result()
}
