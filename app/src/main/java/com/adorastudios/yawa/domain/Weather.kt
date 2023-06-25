package com.adorastudios.yawa.domain

data class Weather(
    val current: WeatherNow,
    val daily: List<WeatherDaily>,
    val hourly: List<WeatherHourly>,
)
