package com.adorastudios.yawa.data.network_module.preferences

import android.content.SharedPreferences

class LocationPreferences(
    private val sharedPreferences: SharedPreferences,
) {
    companion object {
        private const val CURRENT_LOCATION_STRING = "current_location"
        private const val LOCATION_NAME_STRING = "location_name"
        private const val LATITUDE_STRING = "latitude"
        private const val LONGITUDE_STRING = "longitude"
    }

    fun isUseCurrentLocation(): Boolean {
        return sharedPreferences.getBoolean(CURRENT_LOCATION_STRING, true)
    }

    fun setUseCurrentLocation(useCurrentLocation: Boolean) {
        sharedPreferences
            .edit()
            .putBoolean(CURRENT_LOCATION_STRING, true)
            .apply()
    }

    fun getLongitude(): Float {
        return sharedPreferences.getFloat(LONGITUDE_STRING, Float.NaN)
    }

    fun setLongitude(longitude: Float) {
        sharedPreferences
            .edit()
            .putFloat(LONGITUDE_STRING, longitude)
            .apply()
    }

    fun getLatitude(): Float {
        return sharedPreferences.getFloat(LATITUDE_STRING, Float.NaN)
    }

    fun setLatitude(latitude: Float) {
        sharedPreferences
            .edit()
            .putFloat(LATITUDE_STRING, latitude)
            .apply()
    }

    fun getLocationName(): String? {
        return sharedPreferences.getString(LOCATION_NAME_STRING, null)
    }

    fun setLocationName(name: String) {
        sharedPreferences
            .edit()
            .putString(LOCATION_NAME_STRING, name)
            .apply()
    }
}
