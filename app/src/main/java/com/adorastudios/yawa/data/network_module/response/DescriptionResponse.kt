package com.adorastudios.yawa.data.network_module.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DescriptionResponse(
    @SerialName("main")
    val main: String,

    @SerialName("icon")
    val icon: String,
)
