package com.adorastudios.yawa.presentation.utils

sealed class Screen(val route: String) {
    object Splash: Screen("splash")
    object Main: Screen("main")
    object Choose: Screen("main")

    companion object {
        fun toMainScreen(): String = Main.route
    }
}
