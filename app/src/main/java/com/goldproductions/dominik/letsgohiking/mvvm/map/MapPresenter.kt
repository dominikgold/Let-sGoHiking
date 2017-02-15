package com.goldproductions.dominik.letsgohiking.mvvm.map

import com.goldproductions.dominik.letsgohiking.GoHikingApplication
import com.goldproductions.dominik.letsgohiking.service.RouteRecorder
import javax.inject.Inject

class MapPresenter() : MapPresenterIF {

    @Inject
    lateinit var routeRecorder: RouteRecorder

    private var view: MapViewIF? = null

    init {
        GoHikingApplication.graph.inject(this)
    }

    override fun onResume(view: MapViewIF) {
        this.view = view
    }

    override fun onPause() {
        view = null
    }

    override fun requestInitialLocation() {
        routeRecorder.getInitialLocation().subscribe { next ->
            view?.setInitialLocation(next)
        }
    }

    override fun startRouteRecording() {
        routeRecorder.getRecordingObservable().subscribe { next ->
            view?.addToPolyline(next)
        }
    }

}
