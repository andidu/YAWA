package com.adorastudios.yawa.presentation.main_screen.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adorastudios.yawa.asCelsius
import com.adorastudios.yawa.asPrecipitationIcon
import com.adorastudios.yawa.asUVIIcon
import com.adorastudios.yawa.domain.StringOrStringRes
import com.adorastudios.yawa.domain.WeatherDaily

@Composable
fun DayWeather(
    modifier: Modifier = Modifier,
    weather: WeatherDaily,
) {
    val secondaryColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(5f),
            horizontalArrangement = Arrangement.Start,
        ) {
            Text(
                text = when (weather.time) {
                    is StringOrStringRes.Res -> stringResource(id = weather.time.res)
                    is StringOrStringRes.Str -> weather.time.str
                },
                style = MaterialTheme.typography.titleMedium,
                color = secondaryColor,
            )
        }
        Row(
            modifier = Modifier.weight(2f),
            horizontalArrangement = Arrangement.Center,
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
        Row(
            modifier = Modifier.weight(2f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .size(14.dp)
                    .padding(2.dp),
                painter = painterResource(id = weather.uvi.asUVIIcon()),
                contentDescription = null,
                tint = secondaryColor,
            )
            Text(
                modifier = Modifier.padding(bottom = 1.dp),
                text = "${weather.uvi}",
                style = MaterialTheme.typography.labelMedium,
                color = secondaryColor,
            )
        }
        Icon(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = weather.weatherDescription.icon),
            contentDescription = null,
        )
        Row(
            modifier = Modifier.weight(3f),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(
                text = weather.maxTemp.asCelsius(),
            )
            Text(
                text = weather.minTemp.asCelsius(),
            )
        }
    }
}
