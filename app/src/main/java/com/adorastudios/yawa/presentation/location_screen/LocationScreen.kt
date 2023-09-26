@file:OptIn(ExperimentalAnimationApi::class)

package com.adorastudios.yawa.presentation.location_screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.adorastudios.yawa.R
import com.adorastudios.yawa.presentation.MainViewModel
import com.adorastudios.yawa.presentation.components.LoadingImage
import com.adorastudios.yawa.presentation.location_screen.components.LocationTile
import com.adorastudios.yawa.presentation.utils.Screen
import com.adorastudios.yawa.presentation.utils.theme.backgroundColor

@Composable
fun LocationScreen(
    navController: NavController,
    viewModel: MainViewModel,
) {
    val state = viewModel.locationState
    val input = viewModel.input
    val context = LocalContext.current
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {
                    viewModel.onLocationEvent(LocationEvent.CurrentLocationClick(true))
                }
            },
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor()),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = {
                viewModel.loadWeather()
                navController.navigate(Screen.toSplashScreen()) {
                    popUpTo(Screen.toSplashScreen()) {
                        inclusive = true
                    }
                }
            }) {
                Image(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.select_location),
                style = MaterialTheme.typography.titleMedium,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .shadow(elevation = 3.dp, shape = MaterialTheme.shapes.large)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.use_current_location),
                )
                Checkbox(
                    checked = state.value.useCurrentLocation,
                    onCheckedChange = {
                        if (hasLocationPermission(context) || !it) {
                            viewModel.onLocationEvent(LocationEvent.CurrentLocationClick(it))
                        } else {
                            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                        }
                    },
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .shadow(elevation = 3.dp, shape = MaterialTheme.shapes.large)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
        ) {
            Text(
                text = stringResource(id = R.string.currently_selected_location),
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = state.value.currentPlace ?: "",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedContent(targetState = !state.value.useCurrentLocation) { visible ->
            if (visible) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .shadow(elevation = 3.dp, shape = MaterialTheme.shapes.large)
                            .clip(MaterialTheme.shapes.large)
                            .background(MaterialTheme.colorScheme.background)
                            .padding(8.dp)
                            .padding(bottom = 8.dp),
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = input.value,
                            onValueChange = {
                                viewModel.onLocationEvent(LocationEvent.TextInput(it))
                            },
                            label = {
                                Text(text = stringResource(id = R.string.location))
                            },
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    AnimatedContent(state.value.places) { places ->
                        when (places) {
                            is Places.Loaded -> {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                        .shadow(
                                            elevation = 3.dp,
                                            shape = MaterialTheme.shapes.large,
                                        )
                                        .clip(MaterialTheme.shapes.large)
                                        .background(MaterialTheme.colorScheme.background)
                                        .padding(8.dp),
                                ) {
                                    places.list.forEachIndexed { i, address ->
                                        if (i != 0) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                        }

                                        LocationTile(
                                            modifier = Modifier.fillMaxWidth(),
                                            locationName = address.getAddress()
                                                ?: stringResource(id = R.string.select_location),
                                            selected = i == state.value.selected,
                                            onSelect = {
                                                viewModel.onLocationEvent(LocationEvent.Select(i))
                                            },
                                        )
                                    }
                                }
                            }

                            Places.Loading -> {
                                LoadingImage(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                        .shadow(
                                            elevation = 3.dp,
                                            shape = MaterialTheme.shapes.large,
                                        )
                                        .clip(MaterialTheme.shapes.large)
                                        .background(MaterialTheme.colorScheme.background)
                                        .padding(16.dp),
                                )
                            }

                            Places.Nothing -> {}

                            is Places.Error -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                        .shadow(
                                            elevation = 3.dp,
                                            shape = MaterialTheme.shapes.large,
                                        )
                                        .clip(MaterialTheme.shapes.large)
                                        .background(MaterialTheme.colorScheme.errorContainer)
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = stringResource(
                                            when (places) {
                                                Places.Error.UnableToFindCurrent -> R.string.unable_to_find_location
                                                Places.Error.UnableToParseString -> R.string.unable_to_load_location
                                            },
                                        ),
                                        color = MaterialTheme.colorScheme.onErrorContainer,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    ) == PackageManager.PERMISSION_GRANTED
}

fun Address.getAddress(): String? = getAddressLine(0)
