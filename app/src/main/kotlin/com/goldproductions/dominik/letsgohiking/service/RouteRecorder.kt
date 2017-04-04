package com.goldproductions.dominik.letsgohiking.service

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.Single

class RouteRecorder(private val locationManager: LocationManager) : RouteRecorderIF {

    // minimum time between updates in ms
    private val TIME_BETWEEN_UPDATES = 4000L
    // minimum distance between updates in meters
    private val DISTANCE_BETWEEN_UPDATES = 15f

    var currentDistance: Float = 0f

    var currentListener: LocationListener? = null

    val provider: String

    init {
        // for now, only support GPS location services
        provider = LocationManager.GPS_PROVIDER
    }

    override fun getCurrentLocation(): Single<Location> {
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

    override fun getRecordingObservable(): Observable<Location> {
        return Observable.create { subscriber ->
            currentListener = object : LocationListener {

                var lastKnownLocation: Location? = null

                override fun onLocationChanged(location: Location?) {
                    if (location != null) {
                        currentDistance += lastKnownLocation?.distanceTo(location) ?: 0f
                        lastKnownLocation = location
                        subscriber.onNext(location)
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
            }

            locationManager.requestLocationUpdates(provider, TIME_BETWEEN_UPDATES,
                    DISTANCE_BETWEEN_UPDATES, currentListener)
        }
    }

    override fun getDistance(): Float {
        return currentDistance
    }

    override fun resetRouteRecording() {
        currentDistance = 0f
        locationManager.removeUpdates(currentListener)
        currentListener = null
    }

}