package com.adorastudios.yawa.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.adorastudios.yawa.R
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun LoadingImage(
    modifier: Modifier = Modifier,
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
        while (true) {
            delay(500)
            rotation.animateTo(
                targetValue = targetValue,
                animationSpec = tween(
                    durationMillis = 1500,
                ),
            )
            targetValue += 90f
        }
    }

    Box(
        modifier = modifier,
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
}
