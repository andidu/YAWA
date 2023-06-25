package com.adorastudios.yawa.domain

import com.adorastudios.yawa.R

data class Description(
    val main: String,
    val icon: Int,
) {
    companion object {
        val Default = Description("", R.drawable.icon_empty)
    }
}
