package com.goldproductions.dominik.letsgohiking.service

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.goldproductions.dominik.letsgohiking.model.GPSPoint
import io.reactivex.Observable

class RouteRecorder(private val locationManager: LocationManager) {

    // minimum time between updates in ms
    private val TIME_BETWEEN_UPDATES = 2000L
    // minimum distance between updates in meters
    private val DISTANCE_BETWEEN_UPDATES = 10f

    var provider: String
    var routeData: List<GPSPoint>? = null

    init {
        provider = LocationManager.NETWORK_PROVIDER
    }

    fun getInitialLocation(): Observable<GPSPoint?> {
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
                    subscriber.onNext(GPSPoint(location?.longitude!!, location?.latitude!!))
                    subscriber.onComplete()
                }

            }, null)
        }
    }

    fun getRecordingObservable(): Observable<List<GPSPoint>> {
        return Observable.create { subscriber ->
            locationManager.requestLocationUpdates(provider, TIME_BETWEEN_UPDATES, DISTANCE_BETWEEN_UPDATES,
                    object : LocationListener {

                        val listOfPoints: MutableList<GPSPoint> = mutableListOf()
                        val immutableList: List<GPSPoint> = listOfPoints

                        override fun onLocationChanged(location: Location?) {
                            listOfPoints.add(GPSPoint(location?.longitude!!, location?.latitude!!))
                            if (immutableList.size == 5) {
                                subscriber.onNext(immutableList)
                                listOfPoints.clear()
                            }
                        }

                        override fun onProviderDisabled(provider: String?) {

                        }

                        override fun onStatusChanged(provider: String?, status: Int, bundle: Bundle?) {

                        }

                        override fun onProviderEnabled(provider: String?) {

                        }
                    })
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