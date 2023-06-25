package com.adorastudios.yawa.presentation.main_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adorastudios.yawa.R
import com.adorastudios.yawa.domain.WeatherDaily

@Composable
fun DailyWeather(
    modifier: Modifier = Modifier,
    weatherDaily: List<WeatherDaily>,
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.weather_daily),
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column {
            weatherDaily.forEach { dayWeather ->
                DayWeather(
                    modifier = Modifier,
                    weather = dayWeather,
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
