package com.adorastudios.yawa.presentation.main_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.rememberSaveable

@Stable
interface TopBarState {
    val height: Float
    val progress: Float
    var scrollValue: Int
}

@Composable
fun rememberTopBarState(topBarHeightRange: IntRange): TopBarState {
    return rememberSaveable(saver = TopBarStateImpl.Saver) {
        TopBarStateImpl(topBarHeightRange)
    }
}
