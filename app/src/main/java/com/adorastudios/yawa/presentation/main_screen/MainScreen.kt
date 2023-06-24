package com.adorastudios.yawa.presentation.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.rounded.WaterDrop
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
import java.lang.Float.min
import kotlin.math.max

@Composable
fun MainScreen(
    navController: NavController,
) {
    val tolBarHeightRange = with(LocalDensity.current) {
        MinTopBarHeight.roundToPx()..MaxTopBarHeight.roundToPx()
    }
    val topBarState = rememberTopBarState(tolBarHeightRange)
    val scrollState = rememberScrollState()

    topBarState.scrollValue = scrollState.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = MaxTopBarHeight),
        ) {
            for (i in 0..20) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    text = "" + i,
                )
            }
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
                    painter = painterResource(id = R.drawable.sun),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier,
                    text = "24C",
                    fontSize = with(LocalDensity.current) {
                        (24 * (1 + topBarState.progress * 1.5)).dp.toSp()
                    },
                    textAlign = TextAlign.Center,
                )
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(3.dp),
                    imageVector = Icons.Rounded.WaterDrop,
                    contentDescription = stringResource(id = R.string.humidity),
                    tint = secondaryColor,
                )
                Text(
                    modifier = Modifier,
                    text = "24C",
                    fontSize = with(LocalDensity.current) {
                        (14).dp.toSp()
                    },
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
                    text = "Sat, 12:32",
                    fontSize = with(LocalDensity.current) {
                        (14).dp.toSp()
                    },
                    textAlign = TextAlign.Center,
                    color = secondaryColor,
                )
                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clickable {
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
                        text = "Selected city",
                    )
                }
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
