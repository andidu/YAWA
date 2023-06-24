package com.adorastudios.yawa.presentation.main_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.roundToInt

val MinTopBarHeight = 72.dp
val MaxTopBarHeight = 300.dp

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    progress: Float,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        val placeables = measurables.map {
            it.measure(constraints)
        }
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            val horizontalGuideline = (constraints.maxHeight * 0.5f).roundToInt()
            val verticalRightGuideline = (constraints.maxWidth * 0.75f).roundToInt()
            val verticalCenterGuideline = (constraints.maxWidth * 0.5f).roundToInt()
            val verticalLeftGuideline = (constraints.maxWidth * 0.25f).roundToInt()

            val weatherIcon = placeables[0]
            val weatherText = placeables[1]
            val humidityIcon = placeables[2]
            val humidityText = placeables[3]
            val timeIcon = placeables[4]
            val timeText = placeables[5]
            val location = placeables[6]

            location.placeRelative(
                x = verticalCenterGuideline - location.width / 2,
                y = MinTopBarHeight.roundToPx() / 2 - location.height / 2,
            )

            val leftCollapsedCenter = (verticalCenterGuideline - location.width / 2) / 2
            val rightCollapsedCenter =
                (verticalCenterGuideline + location.width / 2) + leftCollapsedCenter

            weatherIcon.placeRelative(
                x = lerp(
                    start = rightCollapsedCenter - weatherIcon.width / 2,
                    stop = verticalRightGuideline - weatherIcon.width / 2,
                    fraction = progress,
                ),
                y = horizontalGuideline - weatherIcon.height / 2,
            )

            val shiftY = (weatherText.height - humidityIcon.height - timeIcon.height) / 2

            weatherText.placeRelative(
                x = lerp(
                    start = leftCollapsedCenter - weatherText.width / 2,
                    stop = verticalLeftGuideline - weatherText.width / 2,
                    fraction = progress,
                ),
                y = lerp(
                    start = horizontalGuideline - weatherText.height / 2,
                    stop = horizontalGuideline - weatherText.height + shiftY,
                    fraction = progress,
                ),
            )

            humidityIcon.placeRelative(
                x = lerp(
                    start = leftCollapsedCenter - weatherText.width / 2,
                    stop = verticalLeftGuideline - weatherText.width / 2,
                    fraction = progress,
                ),
                y = lerp(
                    start = horizontalGuideline + weatherText.height / 2,
                    stop = horizontalGuideline + shiftY,
                    fraction = progress,
                ),
            )
            humidityText.placeRelative(
                x = lerp(
                    start = leftCollapsedCenter - weatherText.width / 2 + humidityIcon.width,
                    stop = verticalLeftGuideline - weatherText.width / 2 + humidityIcon.width,
                    fraction = progress,
                ),
                y = lerp(
                    start = horizontalGuideline + weatherText.height / 2,
                    stop = horizontalGuideline + shiftY,
                    fraction = progress,
                ),
            )
            timeIcon.placeRelative(
                x = lerp(
                    start = leftCollapsedCenter - weatherText.width / 2,
                    stop = verticalLeftGuideline - weatherText.width / 2,
                    fraction = progress,
                ),
                y = lerp(
                    start = horizontalGuideline + weatherText.height / 2 + humidityIcon.height,
                    stop = horizontalGuideline + humidityIcon.height + shiftY,
                    fraction = progress,
                ),
            )
            timeText.placeRelative(
                x = lerp(
                    start = leftCollapsedCenter - weatherText.width / 2 + humidityIcon.width,
                    stop = verticalLeftGuideline - weatherText.width / 2 + timeIcon.width,
                    fraction = progress,
                ),
                y = lerp(
                    start = horizontalGuideline + weatherText.height / 2 + humidityIcon.height,
                    stop = horizontalGuideline + humidityIcon.height + shiftY,
                    fraction = progress,
                ),
            )
        }
    }
}
