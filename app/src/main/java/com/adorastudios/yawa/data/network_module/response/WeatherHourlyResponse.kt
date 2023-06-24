package com.adorastudios.yawa.data.network_module.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherHourlyResponse(
    @SerialName("dt")
    val time: Long,

    @SerialName("temp")
    val temp: Double,

    @SerialName("pop")
    val precipitation: Double,

    @SerialName("weather")
    val weatherDescription: List<DescriptionResponse>,
)
