package com.goldproductions.dominik.letsgohiking.service

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import com.goldproductions.dominik.letsgohiking.model.GPSPoint
import io.reactivex.Observable

class RouteRecorder(private val locationManager: LocationManager) {

    var provider: String
    var locationData: List<GPSPoint>? = null
    var mostRecentLocation: GPSPoint? = null

    init {
        provider = LocationManager.NETWORK_PROVIDER
    }

    fun getCurrentLocation(): Observable<GPSPoint?> {
        return Observable.create { subscriber ->
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
                        mostRecentLocation = GPSPoint(location.longitude, location.latitude)
                    }
                    subscriber.onNext(mostRecentLocation)
                    subscriber.onComplete()
                }

            }, Looper.myLooper())
        }
    }

    class UpdateListener(): LocationListener {
        override fun onLocationChanged(location: Location?) {

        }

        override fun onProviderDisabled(provider: String?) {

        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderEnabled(provider: String?) {

        }

    }

}