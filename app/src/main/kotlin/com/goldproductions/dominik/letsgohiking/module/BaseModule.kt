package com.goldproductions.dominik.letsgohiking.module

import android.content.Context
import android.location.LocationManager
import com.goldproductions.dominik.letsgohiking.service.*
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
    fun provideRouteRecorder(locationManager: LocationManager): RouteRecorderIF {
        return RouteRecorder(locationManager)
    }

    /**
     * Provides the APIClient needed to create the APIClient. Dagger passes the
     * returned APIClient object automatically to provideAPIService()
     */
    @Provides
    @Singleton
    fun provideAPIClient(): APIClient {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://go-hiking-api.herokuapp.com/")
                .build().create(APIClient::class.java)
    }

    @Provides
    @Singleton
    fun provideAPIService(apiClient: APIClient): APIServiceIF {
        return APIService(apiClient)
    }

}