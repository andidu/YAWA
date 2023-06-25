package com.adorastudios.yawa.data.network_module.repository

import com.adorastudios.yawa.domain.Weather

sealed class Result {
    data class OK(val weatherResponse: Weather): Result()
    data class Error(val error: String?): Result()
}
