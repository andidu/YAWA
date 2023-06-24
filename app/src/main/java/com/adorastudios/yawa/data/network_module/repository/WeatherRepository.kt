package com.adorastudios.yawa.data.network_module.repository

interface WeatherRepository {
    suspend fun getWeather(): Result
}
