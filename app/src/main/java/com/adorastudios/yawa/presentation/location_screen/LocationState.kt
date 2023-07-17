package com.adorastudios.yawa.presentation.location_screen

import android.location.Address

data class LocationState(
    val currentPlace: String? = null,
    val currentLon: Float? = null,
    val currentLat: Float? = null,
    val places: Places = Places.Nothing,
    val selected: Int = -1,
)

sealed class Places {
    object Loading: Places()
    object Nothing: Places()
    object Error: Places()
    data class Loaded(val list: List<Address>): Places()
}
