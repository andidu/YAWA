package com.adorastudios.yawa.presentation

import android.location.Geocoder
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adorastudios.yawa.data.network_module.preferences.LocationPreferences
import com.adorastudios.yawa.data.network_module.repository.Result
import com.adorastudios.yawa.data.network_module.repository.WeatherRepository
import com.adorastudios.yawa.presentation.location_screen.LocationEvent
import com.adorastudios.yawa.presentation.location_screen.LocationState
import com.adorastudios.yawa.presentation.location_screen.Places
import com.adorastudios.yawa.presentation.location_screen.getAddress
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
    private val weatherRepository: WeatherRepository,
    private val locationPreferences: LocationPreferences,
    private val geocoder: Geocoder,
): ViewModel() {

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
                                Places.Error
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
            )
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
        }
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
