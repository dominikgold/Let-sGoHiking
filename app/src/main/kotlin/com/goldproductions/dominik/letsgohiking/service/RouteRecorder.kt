package com.goldproductions.dominik.letsgohiking.service

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import io.reactivex.Observable
import io.reactivex.Single

class RouteRecorder(private val locationManager: LocationManager) {

    // minimum time between updates in ms
    private val TIME_BETWEEN_UPDATES = 2000L
    // minimum distance between updates in meters
    private val DISTANCE_BETWEEN_UPDATES = 0f

    var provider: String

    var currentDistance: Float = 0f

    init {
        provider = LocationManager.NETWORK_PROVIDER
    }

    fun getCurrentLocation(): Single<Location> {
        return Single.create { subscriber ->
            locationManager.requestSingleUpdate(provider, object: LocationListener {
                override fun onProviderDisabled(provider: String?) {
                    // do nothing
                }

                override fun onStatusChanged(provider: String?, status: Int, bundle: Bundle?) {
                    //do nothing
                }

                override fun onProviderEnabled(provider: String?) {
                    // do nothing
                }

                override fun onLocationChanged(location: Location?) {
                    if (location != null) {
                        subscriber.onSuccess(location)
                    }
                }

            }, null)
        }
    }

    fun getRecordingObservable(): Observable<List<Location>> {
        return Observable.create { subscriber ->
            Looper.prepare()
            locationManager.requestLocationUpdates(provider, TIME_BETWEEN_UPDATES, DISTANCE_BETWEEN_UPDATES,
                    object : LocationListener {

                        val listOfPoints: MutableList<Location> = mutableListOf()
                        val lastKnownLocation: Location? = null

                        override fun onLocationChanged(location: Location?) {
                            if (location != null) {
                                listOfPoints.add(location)
                                if (lastKnownLocation != null) {
                                    currentDistance += lastKnownLocation.distanceTo(location)
                                }
                                if (listOfPoints.size == 5) {
                                    subscriber.onNext(listOfPoints)
                                    listOfPoints.clear()
                                }
                            }
                        }

                        override fun onProviderDisabled(provider: String?) {
                            // do nothing
                        }

                        override fun onStatusChanged(provider: String?, status: Int, bundle: Bundle?) {
                            // do nothing
                        }

                        override fun onProviderEnabled(provider: String?) {
                            // do nothing
                        }
                    })
        }
    }

}