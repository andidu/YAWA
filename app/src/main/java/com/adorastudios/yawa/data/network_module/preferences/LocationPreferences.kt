package com.adorastudios.yawa.data.network_module.preferences

import android.content.SharedPreferences

class LocationPreferences(
    private val sharedPreferences: SharedPreferences,
) {
    companion object {
        private const val CURRENT_LOCATION_STRING = "current_location"
        private const val FULL_LOCATION_NAME_STRING = "full_location_name"
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
            .putBoolean(CURRENT_LOCATION_STRING, useCurrentLocation)
            .apply()
    }

    fun getLongitude(): Float {
        return sharedPreferences.getFloat(LONGITUDE_STRING, Float.NaN)
    }

    fun getLatitude(): Float {
        return sharedPreferences.getFloat(LATITUDE_STRING, Float.NaN)
    }

    fun getLocationName(): String? {
        return sharedPreferences.getString(LOCATION_NAME_STRING, null)
    }

    fun getFullLocationName(): String? {
        return sharedPreferences.getString(FULL_LOCATION_NAME_STRING, null)
    }

    fun setLocation(fullName: String, name: String, latitude: Float, longitude: Float) {
        sharedPreferences
            .edit()
            .putString(FULL_LOCATION_NAME_STRING, fullName)
            .putString(LOCATION_NAME_STRING, name)
            .putFloat(LATITUDE_STRING, latitude)
            .putFloat(LONGITUDE_STRING, longitude)
            .apply()
    }

    fun setDefaultLocation() {
        sharedPreferences
            .edit()
            .remove(FULL_LOCATION_NAME_STRING)
            .remove(LOCATION_NAME_STRING)
            .remove(LATITUDE_STRING)
            .remove(LONGITUDE_STRING)
            .apply()
    }
}
