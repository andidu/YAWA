@file:OptIn(ExperimentalAnimationApi::class)

package com.adorastudios.yawa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.adorastudios.yawa.presentation.main_screen.MainScreen
import com.adorastudios.yawa.presentation.splash_screen.SplashScreen
import com.adorastudios.yawa.presentation.utils.Screen
import com.adorastudios.yawa.presentation.utils.theme.YAWATheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YAWATheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberAnimatedNavController()

                    AnimatedNavHost(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                        startDestination = Screen.Splash.route,
                    ) {
                        composable(
                            route = Screen.Splash.route,
                            enterTransition = {
                                fadeIn(animationSpec = tween(300))
                            },
                            exitTransition = {
                                fadeOut(animationSpec = tween(300))
                            },
                        ) {
                            SplashScreen(
                                navController = navController,
                            )
                        }
                        composable(
                            route = Screen.Main.route,
                            enterTransition = {
                                fadeIn(animationSpec = tween(300))
                            },
                            exitTransition = {
                                fadeOut(animationSpec = tween(300))
                            },
                        ) {
                            MainScreen(
                                navController = navController,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YAWATheme {
        Greeting("Android")
    }
}
