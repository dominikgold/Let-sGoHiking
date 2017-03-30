package com.goldproductions.dominik.letsgohiking.module

import android.content.Context
import android.location.LocationManager
import com.goldproductions.dominik.letsgohiking.service.APIClient
import com.goldproductions.dominik.letsgohiking.service.APIService
import com.goldproductions.dominik.letsgohiking.service.RouteRecorder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Provides
    @Singleton
    fun provideAPIClient(): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://go-hiking-api.herokuapp.com/")
                .build()
    }

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit): APIService {
        return APIService(retrofit.create(APIClient::class.java))
    }

}