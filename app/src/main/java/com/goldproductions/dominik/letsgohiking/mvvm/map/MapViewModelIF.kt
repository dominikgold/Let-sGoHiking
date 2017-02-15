package com.goldproductions.dominik.letsgohiking.mvvm.map

import com.goldproductions.dominik.letsgohiking.model.GPSPoint
import io.reactivex.Observable

interface MapViewModelIF {
    fun requestCurrentLocation(): Observable<GPSPoint>

    fun startRouteRecording(): Observable<List<GPSPoint>>
}