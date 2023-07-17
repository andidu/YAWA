package com.adorastudios.yawa

import android.app.Application
import android.content.Context
import android.location.Geocoder
import com.adorastudios.yawa.data.network_module.preferences.LocationPreferences
import com.adorastudios.yawa.data.network_module.repository.WeatherRepository
import com.adorastudios.yawa.data.network_module.repository.WeatherRepositoryImpl
import com.adorastudios.yawa.data.network_module.retrofit.AdditionalParamInterceptor
import com.adorastudios.yawa.data.network_module.retrofit.ApiKeyInterceptor
import com.adorastudios.yawa.data.network_module.retrofit.LocationInterceptor
import com.adorastudios.yawa.data.network_module.retrofit.WeatherApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideLocationPreferences(app: Application): LocationPreferences {
        return LocationPreferences(app.getSharedPreferences("location", Context.MODE_PRIVATE))
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(locationPreferences: LocationPreferences): WeatherRepository {
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }

        val contentType = "application/json".toMediaType()

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(LocationInterceptor(locationPreferences))
            .addInterceptor(AdditionalParamInterceptor())
            .addInterceptor(ApiKeyInterceptor())
            .build()

        return WeatherRepositoryImpl(
            Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(json.asConverterFactory(contentType))
                .client(httpClient)
                .build()
                .create(WeatherApi::class.java),
        )
    }

    @Provides
    @Singleton
    fun provideGeocoder(app: Application): Geocoder {
        return Geocoder(app)
    }
}
