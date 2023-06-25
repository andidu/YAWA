package com.adorastudios.yawa.presentation.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.adorastudios.yawa.asCelsius
import com.adorastudios.yawa.asPrecipitationIcon
import com.adorastudios.yawa.domain.WeatherHourly

@Composable
fun HourWeather(
    modifier: Modifier = Modifier,
    weather: WeatherHourly,
) {
    val secondaryColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = weather.time,
            style = MaterialTheme.typography.labelMedium,
            color = secondaryColor,
        )
        Icon(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = weather.weatherDescription.icon),
            contentDescription = null,
        )
        Text(text = weather.temp.asCelsius())
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .size(14.dp)
                    .padding(2.dp),
                painter = painterResource(id = weather.precipitation.asPrecipitationIcon()),
                contentDescription = null,
                tint = secondaryColor,
            )
            Text(
                modifier = Modifier.padding(bottom = 1.dp),
                text = "${weather.precipitation}%",
                style = MaterialTheme.typography.labelMedium,
                color = secondaryColor,
            )
        }
    }
}
