package com.adorastudios.yawa.presentation.location_screen

sealed class LocationEvent {
    data class TextInput(val input: String): LocationEvent()
    data class Select(val index: Int): LocationEvent()
}
