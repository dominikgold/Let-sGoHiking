package com.goldproductions.dominik.letsgohiking.mvvm.map

import com.goldproductions.dominik.letsgohiking.GoHikingApplication
import com.goldproductions.dominik.letsgohiking.service.RouteRecorder
import javax.inject.Inject

class MapPresenter() {

    @Inject
    lateinit var routeRecorder: RouteRecorder

    init {
        GoHikingApplication.graph.inject(this)
    }

}
