package com.goldproductions.dominik.letsgohiking.service

import com.goldproductions.dominik.letsgohiking.model.GPSPoint
import com.goldproductions.dominik.letsgohiking.model.Route
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.exceptions.Exceptions

interface APIServiceIF {
    val client: APIClient

    fun saveRoute(locationData: List<LatLng>?, title: String, distance: Float): Completable

    fun getRoutes(): Single<List<Route>> {
        return Single.create { subscriber ->
            val call = client.getRoutes()
            try {
                val response = call.execute()
                if (response.isSuccessful) {
                    subscriber.onSuccess(response.body().data)
                } else {
                    subscriber.onError(Throwable(response.message()))
                }
            } catch (t: Throwable) {
                Exceptions.propagate(t)
            }
        }
    }

    fun getPointsForRoute(routeId: Int): Single<List<GPSPoint>>
}