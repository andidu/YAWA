package com.adorastudios.yawa.presentation

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.adorastudios.yawa.R
import com.adorastudios.yawa.data.network_module.preferences.LocationPreferences
import com.adorastudios.yawa.data.network_module.repository.Result
import com.adorastudios.yawa.data.network_module.repository.WeatherRepository
import com.adorastudios.yawa.presentation.location_screen.LocationEvent
import com.adorastudios.yawa.presentation.location_screen.LocationState
import com.adorastudios.yawa.presentation.location_screen.Places
import com.adorastudios.yawa.presentation.location_screen.getAddress
import com.adorastudios.yawa.presentation.utils.StringHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val app: Application,
    private val weatherRepository: WeatherRepository,
    private val locationPreferences: LocationPreferences,
    private val geocoder: Geocoder,
    private val locationManager: LocationManager,
    private val stringHelper: StringHelper,
): AndroidViewModel(app) {

    private var _state: MutableState<MainState> = mutableStateOf(MainState.Loading)
    val state: State<MainState> = _state

    private val _input: MutableState<String> = mutableStateOf("")
    val input: State<String> = _input

    private val _locationState: MutableState<LocationState> = mutableStateOf(LocationState())
    val locationState: State<LocationState> = _locationState

    private val flow: MutableSharedFlow<String> = MutableSharedFlow()

    init {
        loadLocation(true)
        subscribeToLocationUpdates()
    }

    private fun subscribeToLocationUpdates() {
        viewModelScope.launch(Dispatchers.IO) {
            flow
                .onEach {
                    withContext(Dispatchers.Main) {
                        _locationState.value = locationState.value.copy(
                            places = Places.Loading,
                            selected = -1,
                        )
                    }
                }
                .debounce(1000)
                .collectLatest { name ->
                    val list = try {
                        geocoder.getFromLocationName(name, 5)
                    } catch (_: Exception) {
                        null
                    }
                    withContext(Dispatchers.Main) {
                        _locationState.value = locationState.value.copy(
                            places = if (list.isNullOrEmpty()) {
                                Places.Error.UnableToParseString
                            } else {
                                Places.Loaded(list)
                            },
                        )
                    }
                }
        }
    }

    private fun loadLocation(loadWeather: Boolean) {
        viewModelScope.launch {
            _locationState.value = locationState.value.copy(
                currentPlace = locationPreferences.getFullLocationName(),
                currentLon = locationPreferences.getLongitude(),
                currentLat = locationPreferences.getLatitude(),
                useCurrentLocation = locationPreferences.isUseCurrentLocation(),
            )
            if (_locationState.value.useCurrentLocation) {
                val location = getLocation()
                if (location != null) {
                    findAndSetLocation(location)
                }
            }
        }.invokeOnCompletion {
            if (loadWeather) {
                loadWeather()
            }
        }
    }

    fun onLocationEvent(event: LocationEvent) {
        when (event) {
            is LocationEvent.TextInput -> {
                _input.value = event.input
                viewModelScope.launch {
                    if (event.input.length > 1) {
                        flow.emit(event.input)
                    } else {
                        _locationState.value = _locationState.value.copy(
                            places = Places.Nothing,
                        )
                    }
                }
            }

            is LocationEvent.Select -> {
                val places = locationState.value.places
                if (places !is Places.Loaded) return
                if (places.list.size <= event.index) return

                _locationState.value = locationState.value.copy(
                    selected = event.index,
                )

                viewModelScope.launch(Dispatchers.IO) {
                    val place = places.list[event.index]
                    locationPreferences.setLocation(
                        fullName = place.getAddress() ?: "",
                        name = place.getAddress()?.split(",")?.get(0) ?: "",
                        latitude = place.latitude.toFloat(),
                        longitude = place.longitude.toFloat(),
                    )
                    withContext(Dispatchers.IO) {
                        _locationState.value = locationState.value.copy(
                            currentPlace = place.getAddress() ?: "",
                        )
                    }
                }
            }

            is LocationEvent.CurrentLocationClick -> {
                _locationState.value = locationState.value.copy(
                    useCurrentLocation = event.value,
                )
                viewModelScope.launch(Dispatchers.IO) {
                    locationPreferences.setUseCurrentLocation(event.value)
                    if (event.value) {
                        val location = getLocation()
                        if (location == null) {
                            _locationState.value = locationState.value.copy(
                                places = Places.Error.UnableToFindCurrent,
                            )
                        } else {
                            findAndSetLocation(location)
                        }
                    } else {
                        locationPreferences.setDefaultLocation()
                        _locationState.value = locationState.value.copy(
                            currentPlace = locationPreferences.getFullLocationName(),
                            currentLon = locationPreferences.getLongitude(),
                            currentLat = locationPreferences.getLatitude(),
                        )
                    }
                }
            }
        }
    }

    private fun findAndSetLocation(location: Location) {
        val foundLocation = geocoder
            .getFromLocation(location.latitude, location.longitude, 1)
            ?.firstOrNull()

        val locationName = if (foundLocation != null) {
            stringHelper.get(R.string.current_location)
        } else {
            stringHelper.get(R.string.unknown_location)
        }
        val locationFullName = foundLocation?.getAddress()?.let {
            it + " - ${stringHelper.get(R.string.unknown_location)}"
        } ?: stringHelper.get(R.string.unknown_location)
        val longitude = location.longitude
        val latitude = location.latitude

        locationPreferences.setLocation(
            name = locationName,
            fullName = locationFullName,
            longitude = longitude.toFloat(),
            latitude = latitude.toFloat(),
        )

        _locationState.value = locationState.value.copy(
            currentPlace = locationFullName,
            currentLon = longitude.toFloat(),
            currentLat = latitude.toFloat(),
        )
    }

    private fun getLocation(): Location? {
        val providers = locationManager.getProviders(true)
        var bestLocation: Location? = null
        for (provider in providers) {
            val l = if (ActivityCompat.checkSelfPermission(
                    app,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    app,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(
                    app,
                    stringHelper.get(R.string.unable_to_access_current_location),
                    Toast.LENGTH_SHORT,
                ).show()
                null
            } else {
                locationManager.getLastKnownLocation(provider)
            } ?: continue
            if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                bestLocation = l
            }
        }
        return bestLocation
    }

    fun loadWeather() {
        _state.value = MainState.Loading

        viewModelScope.launch {
            when (val weather = weatherRepository.getWeather()) {
                is Result.Error -> {
                    _state.value = MainState.Loaded.Error(weather.error)
                }

                is Result.OK -> {
                    _state.value = MainState.Loaded.OK(
                        weather = weather.weatherResponse,
                        location = locationPreferences.getLocationName(),
                    )
                }
            }
        }
    }
}
