package com.goldproductions.dominik.letsgohiking.service

import com.goldproductions.dominik.letsgohiking.model.GPSPoint
import com.goldproductions.dominik.letsgohiking.model.Route
import com.goldproductions.dominik.letsgohiking.model.RouteWithPoints
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.exceptions.Exceptions

class APIService(private val client: APIClient) {

    /**
     * returns a Completable object that sends a synchronous POST request and signals whether the save was successful or
     * not
     */
    fun saveRoute(locationData: List<LatLng>?, title: String, distance: Float): Completable {
        val route: RouteWithPoints = RouteWithPoints(mapLatLngListToGPSPoints(locationData), title, distance)
        return Completable.create { subscriber ->
            val call = client.saveRoute(route)
            try {
                val response = call.execute()
                if (response.isSuccessful) {
                    subscriber.onComplete()
                } else {
                    subscriber.onError(Throwable(response.message()))
                }
            } catch (t: Throwable) {
                Exceptions.propagate(t)
            }
        }
    }

    private fun mapLatLngListToGPSPoints(locationData: List<LatLng>?): List<GPSPoint> {
        val newList: MutableList<GPSPoint> = mutableListOf()
        locationData?.forEach { latLng ->
            newList.add(GPSPoint(latLng.latitude, latLng.longitude))
        }
        return newList
    }

    /**
     * returns a Single object that requests all routes from the database synchronously.
     */
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

    /**
     * returns a Single object that requests the points associated with the given route id synchronously.
     */
    fun getPointsForRoute(routeId: Int): Single<List<GPSPoint>> {
        return Single.create { subscriber ->
            val call = client.getPointsForRoute(routeId)
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

}