package com.adorastudios.yawa.presentation.utils

import android.content.Context

class StringHelper(private val context: Context) {
    fun get(id: Int): String {
        return context.getString(id)
    }
}
