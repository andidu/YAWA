package com.adorastudios.yawa

import android.app.Application
import android.content.Context
import com.adorastudios.yawa.data.network_module.preferences.LocationPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideLocationPreferences(app: Application): LocationPreferences {
        return LocationPreferences(app.getSharedPreferences("location", Context.MODE_PRIVATE))
    }
}
