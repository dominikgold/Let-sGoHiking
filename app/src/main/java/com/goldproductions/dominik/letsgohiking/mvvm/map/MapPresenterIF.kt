package com.goldproductions.dominik.letsgohiking.mvvm.map

interface MapPresenterIF {
    fun onResume(view: MapViewIF)

    fun onPause()

    fun requestInitialLocation()

    fun startRouteRecording()
}