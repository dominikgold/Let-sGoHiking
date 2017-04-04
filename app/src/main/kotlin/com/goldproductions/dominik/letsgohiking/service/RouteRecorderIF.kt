package com.goldproductions.dominik.letsgohiking.service

import android.location.Location
import io.reactivex.Observable
import io.reactivex.Single

interface RouteRecorderIF {
    fun getCurrentLocation(): Single<Location>

    fun getRecordingObservable(): Observable<Location>

    fun resetRouteRecording()

    fun getDistance(): Float
}