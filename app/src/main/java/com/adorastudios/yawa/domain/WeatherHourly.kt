package com.adorastudios.yawa.domain

data class WeatherHourly(
    val time: String,
    val temp: Int,
    val precipitation: Int,
    val weatherDescription: Description,
)
