package com.adorastudios.yawa.data.network_module.repository

import com.adorastudios.yawa.data.network_module.response.WeatherResponse
import com.adorastudios.yawa.data.network_module.retrofit.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(private val api: WeatherApi): WeatherRepository {
    override suspend fun getWeather(): Result {
        return try {
            withContext(Dispatchers.IO) {
                api.get().let { weatherResponse ->
                    Result.OK(
                        WeatherResponse(
                            current = weatherResponse.current,
                            daily = weatherResponse.daily.take(7),
                            hourly = weatherResponse.hourly.take(24),
                        ),
                    )
                }
            }
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }
}
