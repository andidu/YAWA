package com.adorastudios.yawa.presentation.location_screen

import android.location.Address

data class LocationState(
    val currentPlace: String? = null,
    val currentLon: Float? = null,
    val currentLat: Float? = null,
    val places: Places = Places.Nothing,
    val selected: Int = -1,
    val useCurrentLocation: Boolean = false,
)

sealed class Places {
    object Loading: Places()
    object Nothing: Places()
    sealed class Error: Places() {
        object UnableToFindCurrent: Error()
        object UnableToParseString: Error()
    }
    data class Loaded(val list: List<Address>): Places()
}
