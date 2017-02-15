package com.goldproductions.dominik.letsgohiking.mvvm.map

import com.goldproductions.dominik.letsgohiking.GoHikingApplication
import com.goldproductions.dominik.letsgohiking.model.GPSPoint
import com.goldproductions.dominik.letsgohiking.service.RouteRecorder
import io.reactivex.Observable
import javax.inject.Inject

class MapViewModel() : MapViewModelIF {

    @Inject
    lateinit var routeRecorder: RouteRecorder

    init {
        GoHikingApplication.graph.inject(this)
    }

    override fun requestCurrentLocation(): Observable<GPSPoint> {
        return routeRecorder.getCurrentLocation()
    }

    override fun startRouteRecording(): Observable<List<GPSPoint>> {
        return routeRecorder.getRecordingObservable()
    }

}
