package com.goldproductions.dominik.letsgohiking.module

import android.content.Context
import android.location.LocationManager
import com.goldproductions.dominik.letsgohiking.service.RouteRecorder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BaseModule(val applicationContext: Context) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return applicationContext
    }

    @Provides
    fun provideLocationManager(applicationContext: Context): LocationManager {
        return applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @Provides
    fun provideRouteRecorder(locationManager: LocationManager): RouteRecorder {
        return RouteRecorder(locationManager)
    }

}