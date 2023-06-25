package com.adorastudios.yawa.data.network_module.repository

import com.adorastudios.yawa.asDayFormatted
import com.adorastudios.yawa.asTimeFormatted
import com.adorastudios.yawa.asWindDir
import com.adorastudios.yawa.data.network_module.retrofit.WeatherApi
import com.adorastudios.yawa.domain.Weather
import com.adorastudios.yawa.domain.WeatherDaily
import com.adorastudios.yawa.domain.WeatherHourly
import com.adorastudios.yawa.domain.WeatherNow
import com.adorastudios.yawa.firstOrDefault
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class WeatherRepositoryImpl(private val api: WeatherApi): WeatherRepository {
    override suspend fun getWeather(): Result {
        return try {
            withContext(Dispatchers.IO) {
                api.get().let { weatherResponse ->
                    Result.OK(
                        Weather(
                            current = weatherResponse.current.let { response ->
                                WeatherNow(
                                    time = (response.time + weatherResponse.timeOffset).asTimeFormatted(),
                                    temp = response.temp.roundToInt(),
                                    feelsLike = response.feelsLike.roundToInt(),
                                    weatherDescription = response.weatherDescription.firstOrDefault(),
                                    humidity = response.humidity,
                                    sunrise = (response.sunrise + weatherResponse.timeOffset).asTimeFormatted(),
                                    sunset = (response.sunset + weatherResponse.timeOffset).asTimeFormatted(),
                                    windSpeed = response.windSpeed.roundToInt(),
                                    windDir = response.windDir.asWindDir(),
                                    uvi = response.uvi.roundToInt(),
                                )
                            },
                            daily = weatherResponse.daily.take(7).map { daily ->
                                WeatherDaily(
                                    time = daily.time.asDayFormatted(weatherResponse.timeOffset),
                                    maxTemp = daily.temp.max.roundToInt(),
                                    minTemp = daily.temp.min.roundToInt(),
                                    precipitation = daily.precipitation.times(100).roundToInt(),
                                    weatherDescription = daily.weatherDescription.firstOrDefault(),
                                    uvi = daily.uvi.roundToInt(),
                                )
                            },
                            hourly = weatherResponse.hourly.take(48).map { hourly ->
                                WeatherHourly(
                                    time = (hourly.time + weatherResponse.timeOffset).asTimeFormatted(),
                                    temp = hourly.temp.roundToInt(),
                                    precipitation = hourly.precipitation.times(100).roundToInt(),
                                    weatherDescription = hourly.weatherDescription.firstOrDefault(),
                                )
                            },
                        ),
                    )
                }
            }
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }
}
