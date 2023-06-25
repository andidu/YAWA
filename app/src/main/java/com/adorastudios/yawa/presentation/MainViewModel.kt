package com.adorastudios.yawa.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adorastudios.yawa.data.network_module.preferences.LocationPreferences
import com.adorastudios.yawa.data.network_module.repository.Result
import com.adorastudios.yawa.data.network_module.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationPreferences: LocationPreferences,
): ViewModel() {

    private var _state: MutableState<MainState> = mutableStateOf(MainState.Loading)
    val state: State<MainState> = _state

    init {
        loadWeather()
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
