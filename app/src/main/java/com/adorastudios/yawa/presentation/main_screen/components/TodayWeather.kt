@file:OptIn(ExperimentalLayoutApi::class)

package com.adorastudios.yawa.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adorastudios.yawa.R
import com.adorastudios.yawa.asUVIIcon
import com.adorastudios.yawa.domain.WeatherNow

@Composable
fun TodayWeather(
    modifier: Modifier = Modifier,
    weatherNow: WeatherNow,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom,
        maxItemsInEachRow = 2,
    ) {
        TodayWeatherItem(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1.5f)
                .shadow(elevation = 3.dp, shape = MaterialTheme.shapes.large)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
            text = stringResource(id = R.string.humidity),
            value = "${weatherNow.humidity}%",
            icon = painterResource(id = R.drawable.humidity),
            iconPadding = PaddingValues(4.dp),
        )
        TodayWeatherItem(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1.5f)
                .shadow(elevation = 3.dp, shape = MaterialTheme.shapes.large)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
            text = stringResource(id = R.string.uvi),
            value = "${weatherNow.uvi}",
            icon = painterResource(id = weatherNow.uvi.asUVIIcon()),
            iconPadding = PaddingValues(4.dp),
        )
        TodayWeatherItem(
            modifier = Modifier
                .padding(top = 8.dp)
                .weight(1f)
                .aspectRatio(1.5f)
                .shadow(elevation = 3.dp, shape = MaterialTheme.shapes.large)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
            text = stringResource(id = R.string.sunrise),
            value = weatherNow.sunrise,
            icon = painterResource(id = R.drawable.sunrise),
        )
        TodayWeatherItem(
            modifier = Modifier
                .padding(top = 8.dp)
                .weight(1f)
                .aspectRatio(1.5f)
                .shadow(elevation = 3.dp, shape = MaterialTheme.shapes.large)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
            text = stringResource(id = R.string.sunset),
            value = weatherNow.sunset,
            icon = painterResource(id = R.drawable.sunset),
        )
        TodayWeatherItem(
            modifier = Modifier
                .padding(top = 8.dp)
                .weight(1f)
                .aspectRatio(1.5f)
                .shadow(elevation = 3.dp, shape = MaterialTheme.shapes.large)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
            text = stringResource(id = R.string.wind_speed),
            value = "${weatherNow.windSpeed}m/s",
            icon = painterResource(id = R.drawable.wind),
        )
        TodayWeatherItem(
            modifier = Modifier
                .padding(top = 8.dp)
                .weight(1f)
                .aspectRatio(1.5f)
                .shadow(elevation = 3.dp, shape = MaterialTheme.shapes.large)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
            text = stringResource(id = R.string.wind_direction),
            value = weatherNow.windDir,
            icon = painterResource(id = R.drawable.wind_dir),
            iconPadding = PaddingValues(4.dp),
        )
    }
}

@Composable
fun TodayWeatherItem(
    modifier: Modifier = Modifier,
    text: String,
    value: String,
    icon: Painter,
    iconPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            modifier = Modifier
                .size(32.dp)
                .padding(iconPadding),
            contentDescription = text,
            painter = icon,
        )
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = text,
        )
        Text(
            text = value,
        )
    }
}
