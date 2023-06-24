package com.adorastudios.yawa.data.network_module.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("current")
    val current: WeatherNowResponse,

    @SerialName("hourly")
    val hourly: List<WeatherHourlyResponse>,

    @SerialName("daily")
    val daily: List<WeatherDailyResponse>,
)
