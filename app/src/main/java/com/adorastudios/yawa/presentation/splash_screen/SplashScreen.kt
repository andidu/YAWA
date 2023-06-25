package com.adorastudios.yawa.presentation.splash_screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.adorastudios.yawa.R
import com.adorastudios.yawa.presentation.MainState
import com.adorastudios.yawa.presentation.MainViewModel
import com.adorastudios.yawa.presentation.utils.Screen
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: MainViewModel,
) {
    val rotation = remember {
        Animatable(0f)
    }
    val day = remember {
        Calendar.getInstance().let { calendar ->
            val hour = (calendar.timeInMillis + calendar.timeZone.rawOffset) / 1000 / 60 / 60 % 24
            hour in 6..18
        }
    }

    LaunchedEffect(key1 = true) {
        var targetValue = 90f
        while (viewModel.state.value is MainState.Loading) {
            delay(500)
            rotation.animateTo(
                targetValue = targetValue,
                animationSpec = tween(
                    durationMillis = 1500,
                ),
            )
            targetValue += 90f
        }
        delay(500)
        navController.navigate(Screen.toMainScreen())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            if (day) {
                Icon(
                    modifier = Modifier
                        .graphicsLayer {
                            rotationZ = rotation.value
                        }
                        .scale(1.5f),
                    painter = painterResource(id = R.drawable.icon_01d),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            } else {
                Icon(
                    modifier = Modifier
                        .graphicsLayer {
                            rotationZ = -rotation.value
                        }
                        .scale(1.5f),
                    painter = painterResource(id = R.drawable.icon_01n),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.app_description),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
