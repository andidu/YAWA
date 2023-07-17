package com.adorastudios.yawa.presentation.utils

sealed class Screen(val route: String) {
    object Splash: Screen("splash")
    object Main: Screen("main")
    object Location: Screen("location")

    companion object {
        fun toMainScreen(): String = Main.route
        fun toLocationScreen(): String = Location.route
        fun toSplashScreen(): String = Splash.route
    }
}
