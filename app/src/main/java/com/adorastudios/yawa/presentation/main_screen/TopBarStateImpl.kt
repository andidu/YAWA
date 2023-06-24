package com.adorastudios.yawa.presentation.main_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy

class TopBarStateImpl(
    heightRange: IntRange,
    scrollValue: Int = 0,
): TopBarState {
    private val minHeight = heightRange.first
    private val maxHeight = heightRange.last
    private val rangeDifference = heightRange.last - heightRange.first

    private var _scrollValue by mutableStateOf(
        value = scrollValue.coerceAtLeast(0),
        policy = structuralEqualityPolicy(),
    )

    override var scrollValue: Int
        get() = _scrollValue
        set(value) {
            _scrollValue = value.coerceAtLeast(0)
        }

    override val progress: Float
        get() = 1 - (maxHeight - height) / rangeDifference

    override val height: Float
        get() = (maxHeight.toFloat() - scrollValue).coerceIn(
            minHeight.toFloat(),
            maxHeight.toFloat(),
        )

    companion object {
        val Saver = run {

            val minHeightKey = "MinHeight"
            val maxHeightKey = "MaxHeight"
            val scrollValueKey = "ScrollValue"

            mapSaver(
                save = {
                    mapOf(
                        minHeightKey to it.minHeight,
                        maxHeightKey to it.maxHeight,
                        scrollValueKey to it.scrollValue,
                    )
                },
                restore = {
                    TopBarStateImpl(
                        heightRange = (it[minHeightKey] as Int)..(it[maxHeightKey] as Int),
                        scrollValue = it[scrollValueKey] as Int,
                    )
                },
            )
        }
    }
}
