package com.adorastudios.yawa.data.network_module.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyTemperatureResponse(
    @SerialName("min")
    val min: Double,

    @SerialName("max")
    val max: Double,
)
