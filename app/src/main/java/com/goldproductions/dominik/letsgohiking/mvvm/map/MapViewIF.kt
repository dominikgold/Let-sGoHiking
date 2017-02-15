package com.goldproductions.dominik.letsgohiking.mvvm.map

import com.goldproductions.dominik.letsgohiking.model.GPSPoint

interface MapViewIF {
    fun setInitialLocation(next: GPSPoint?)

    fun addToPolyline(points: List<GPSPoint>)
}