package com.adorastudios.yawa.data.network_module.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherNowResponse(
    @SerialName("dt")
    val time: Long,

    @SerialName("temp")
    val temp: Double,

    @SerialName("feels_like")
    val feelsLike: Double,

    @SerialName("weather")
    val weatherDescription: List<DescriptionResponse>,

    @SerialName("humidity")
    val humidity: Int,

    @SerialName("sunrise")
    val sunrise: Long,

    @SerialName("sunset")
    val sunset: Long,

    @SerialName("wind_speed")
    val windSpeed: Double,

    @SerialName("wind_deg")
    val windDir: Int,

    @SerialName("uvi")
    val uvi: Double,
)
