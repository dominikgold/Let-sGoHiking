package com.goldproductions.dominik.letsgohiking.service

import com.goldproductions.dominik.letsgohiking.model.GPSPoint
import com.goldproductions.dominik.letsgohiking.model.Route
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Completable
import io.reactivex.Single

interface APIServiceIF {
    fun saveRoute(locationData: List<LatLng>?, title: String, distance: Float): Completable

    fun getRoutes(): Single<List<Route>>

    fun getPointsForRoute(routeId: Int): Single<List<GPSPoint>>
}