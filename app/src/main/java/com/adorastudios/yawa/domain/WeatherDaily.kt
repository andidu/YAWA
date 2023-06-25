package com.adorastudios.yawa.domain

data class WeatherDaily(
    val time: StringOrStringRes,
    val maxTemp: Int,
    val minTemp: Int,
    val precipitation: Int,
    val weatherDescription: Description,
    val uvi: Int,
)

sealed class StringOrStringRes {
    data class Str(val str: String): StringOrStringRes()
    data class Res(val res: Int): StringOrStringRes()
}
