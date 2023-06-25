package com.adorastudios.yawa.presentation.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adorastudios.yawa.R
import com.adorastudios.yawa.domain.WeatherHourly

@Composable
fun HourlyWeather(
    modifier: Modifier = Modifier,
    weatherHourly: List<WeatherHourly>,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.weather_48),
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = weatherHourly,
            ) { weather ->
                HourWeather(
                    weather = weather,
                )
            }
        }
    }
}
