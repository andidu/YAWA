package com.adorastudios.yawa.domain

data class WeatherNow(
    val time: String,
    val temp: Int,
    val feelsLike: Int,
    val weatherDescription: Description,
    val humidity: Int,
    val sunrise: String,
    val sunset: String,
    val windSpeed: Int,
    val windDir: String,
    val uvi: Int,
)
