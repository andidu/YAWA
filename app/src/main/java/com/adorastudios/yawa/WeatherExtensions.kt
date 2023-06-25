package com.adorastudios.yawa

import com.adorastudios.yawa.data.network_module.response.DescriptionResponse
import com.adorastudios.yawa.domain.Description
import com.adorastudios.yawa.domain.StringOrStringRes
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.SimpleTimeZone

fun Int.asWindDir(): String {
    return if (this < 22.5) {
        "N"
    } else if (this < 67.5) {
        "NE"
    } else if (this < 112.5) {
        "E"
    } else if (this < 157.5) {
        "SE"
    } else if (this < 202.5) {
        "S"
    } else if (this < 247.5) {
        "SW"
    } else if (this < 292.5) {
        "W"
    } else if (this < 337.5) {
        "NW"
    } else {
        "N"
    }
}

fun Long.asTimeFormatted(): String {
    val df = Date(this * 1000)
    val simpleDateFormat = SimpleDateFormat.getTimeInstance(DateFormat.SHORT)
    simpleDateFormat.timeZone = SimpleTimeZone(0, "")
    return simpleDateFormat.format(df)
}

const val MILLIS_IN_DAY = 24 * 60 * 60 * 1000

fun Long.asDayFormatted(offset: Int): StringOrStringRes {
    val df = (this + offset) * 1000
    val nowMillis = System.currentTimeMillis() + offset * 1000

    return if (df / MILLIS_IN_DAY == nowMillis / MILLIS_IN_DAY) {
        StringOrStringRes.Res(R.string.today)
    } else if (df / MILLIS_IN_DAY == nowMillis / MILLIS_IN_DAY + 1) {
        StringOrStringRes.Res(R.string.tomorrow)
    } else {
        StringOrStringRes.Str(SimpleDateFormat.getDateInstance().format(Date(df)))
    }
}

fun List<DescriptionResponse>.firstOrDefault(): Description {
    return this.firstOrNull()
        ?.let { description ->
            Description(description.main, description.icon.asResourceId())
        } ?: Description.Default
}

fun Int.asCelsius(): String {
    return String.format("%d°", this)
}

fun String.asResourceId(): Int {
    return when (this) {
        "01d", "02d" -> R.drawable.icon_01d
        "01n", "02n" -> R.drawable.icon_01n
        "03d" -> R.drawable.icon_02d
        "03n" -> R.drawable.icon_02n
        "04d", "04n" -> R.drawable.icon_03
        "09d", "09n" -> R.drawable.icon_09
        "10d" -> R.drawable.icon_10d
        "10n" -> R.drawable.icon_10n
        "11d", "11n" -> R.drawable.icon_11
        "13d", "13n" -> R.drawable.icon_13
        "50d", "50n" -> R.drawable.icon_50
        else -> R.drawable.icon_empty
    }
}

fun Int.asPrecipitationIcon(): Int {
    return if (this < 50) {
        R.drawable.precipitation
    } else {
        R.drawable.precipitation_high
    }
}

fun Int.asUVIIcon(): Int {
    return if (this < 6) {
        R.drawable.uvi
    } else {
        R.drawable.uvi_high
    }
}
