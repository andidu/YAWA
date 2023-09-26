package com.adorastudios.yawa.presentation.main_screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PinDrop
import androidx.compose.material.icons.rounded.Update
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.adorastudios.yawa.R
import com.adorastudios.yawa.asCelsius
import com.adorastudios.yawa.presentation.MainState
import com.adorastudios.yawa.presentation.MainViewModel
import com.adorastudios.yawa.presentation.main_screen.components.DailyWeather
import com.adorastudios.yawa.presentation.main_screen.components.HourlyWeather
import com.adorastudios.yawa.presentation.main_screen.components.TodayWeather
import com.adorastudios.yawa.presentation.utils.Screen
import com.adorastudios.yawa.presentation.utils.theme.backgroundColor
import java.lang.Float.min
import kotlin.math.max

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel,
) {
    val state = viewModel.state.value

    val tolBarHeightRange = with(LocalDensity.current) {
        MinTopBarHeight.roundToPx()..MaxTopBarHeight.roundToPx()
    }
    val topBarState = rememberTopBarState(tolBarHeightRange)
    val scrollState = rememberScrollState()

    topBarState.scrollValue = scrollState.value

    Crossfade(targetState = state) { targetState ->
        when (targetState) {
            is MainState.Loaded.OK -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                            .background(backgroundColor())
                            .padding(top = MaxTopBarHeight),
                    ) {
                        TodayWeather(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .padding(top = 16.dp)
                                .fillMaxWidth(),
                            weatherNow = targetState.weather.current,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        HourlyWeather(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .fillMaxWidth()
                                .shadow(elevation = 3.dp, shape = MaterialTheme.shapes.large)
                                .clip(MaterialTheme.shapes.large)
                                .background(MaterialTheme.colorScheme.background)
                                .padding(8.dp),
                            weatherHourly = targetState.weather.hourly,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        DailyWeather(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .padding(bottom = 16.dp)
                                .fillMaxWidth()
                                .shadow(elevation = 3.dp, shape = MaterialTheme.shapes.large)
                                .clip(MaterialTheme.shapes.large)
                                .background(MaterialTheme.colorScheme.background)
                                .padding(8.dp),
                            weatherDaily = targetState.weather.daily,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(with(LocalDensity.current) { topBarState.height.toDp() }),
                    ) {
                        Box(
                            modifier = Modifier
                                .background(Color.Transparent)
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .height(10.dp)
                                .shadow(elevation = 6.dp),
                        )
                        TopBar(
                            modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                            progress = topBarState.progress,
                        ) {
                            val secondaryColor = countColor(topBarState.progress)

                            Icon(
                                modifier = Modifier.size((48 * (1 + topBarState.progress * 2.75)).dp),
                                painter = painterResource(id = targetState.weather.current.weatherDescription.icon),
                                contentDescription = null,
                            )
                            Text(
                                modifier = Modifier,
                                text = targetState.weather.current.temp.asCelsius(),
                                fontSize = with(LocalDensity.current) {
                                    (24 * (1 + topBarState.progress * 1.5)).dp.toSp()
                                },
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                modifier = Modifier,
                                text = stringResource(id = R.string.feels_like) + targetState.weather.current.feelsLike.asCelsius(),
                                fontSize = with(LocalDensity.current) { 14.dp.toSp() },
                                textAlign = TextAlign.Center,
                                color = secondaryColor,
                            )
                            Icon(
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(3.dp),
                                imageVector = Icons.Rounded.Update,
                                contentDescription = null,
                                tint = secondaryColor,
                            )
                            Text(
                                modifier = Modifier,
                                text = targetState.weather.current.time,
                                fontSize = with(LocalDensity.current) { 14.dp.toSp() },
                                textAlign = TextAlign.Center,
                                color = secondaryColor,
                            )
                            Row(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .clickable {
                                        navController.navigate(Screen.toLocationScreen())
                                    }
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(2.dp),
                                    imageVector = Icons.Rounded.PinDrop,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                                Text(
                                    modifier = Modifier.padding(bottom = 2.dp, end = 4.dp),
                                    text = targetState.location
                                        ?: stringResource(id = R.string.select_location),
                                )
                            }
                        }
                    }
                }
            }

            is MainState.Loaded.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(id = R.string.unable_to_load),
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    targetState.message?.let {
                        Text(
                            text = targetState.message,
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Button(onClick = { viewModel.loadWeather() }) {
                        Text(
                            text = stringResource(id = R.string.try_again),
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = { navController.navigate(Screen.toLocationScreen()) }) {
                        Text(
                            text = stringResource(id = R.string.change_location),
                        )
                    }
                }
            }

            MainState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun countColor(progress: Float): Color {
    return MaterialTheme.colorScheme.onBackground.copy(
        alpha = max(min(0.8f, (-0.2f + 1.25 * progress).toFloat()), 0f),
    )
}
