package com.adorastudios.yawa.presentation

import com.adorastudios.yawa.domain.Weather

sealed class MainState {
    object Loading: MainState()

    sealed class Loaded {
        data class OK(
            val location: String?,
            val weather: Weather,
        ): MainState()

        data class Error(val message: String?): MainState()
    }
}
