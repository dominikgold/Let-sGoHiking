package com.goldproductions.dominik.letsgohiking.mvp.map

import com.goldproductions.dominik.letsgohiking.GoHikingApplication
import com.goldproductions.dominik.letsgohiking.mvp.base.BasePresenter
import com.goldproductions.dominik.letsgohiking.service.APIService
import com.goldproductions.dominik.letsgohiking.service.RouteRecorder
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapPresenter() : BasePresenter<MapView>() {

    @Inject
    lateinit var routeRecorder: RouteRecorder

    @Inject
    lateinit var apiService: APIService

    private var routeRecordingObservable: Observable<List<LatLng>>? = null

    private var currentLocationSingle: Single<LatLng>? = null

    private var initialMarkerLocationSingle: Single<LatLng>? = null

    private var saveRouteCompletable: Completable? = null

    private var locationData: MutableList<LatLng>? = null

    var isRecording: Boolean = false

    init {
        GoHikingApplication.graph.inject(this)
    }

    /**
     * this is used to get the initial location of the user on app startup to position the map camera
     */
    fun initCurrentLocationSingle() {
        if (currentLocationSingle == null) {
            currentLocationSingle = routeRecorder.getCurrentLocation().map<LatLng>(Function { next->
                LatLng(next.latitude, next.longitude)
            }).cache()
            requestCurrentLocation()
        }
    }

    private fun requestCurrentLocation() {
        if (currentLocationSingle != null) {
            disposables?.add(currentLocationSingle?.subscribe { next ->
                view?.setInitialLocation(next)
                currentLocationSingle = null
            })
        }
    }

    /**
     * this is used to get the initial location of the user when he starts the route recording
     */
    fun initMarkerLocationSingle() {
        if (initialMarkerLocationSingle == null) {
            initialMarkerLocationSingle = routeRecorder.getCurrentLocation().map<LatLng>(Function { next ->
                LatLng(next.latitude, next.longitude)
            }).cache()
            requestInitialMarkerLocation()
        }
    }

    private fun requestInitialMarkerLocation() {
        if (initialMarkerLocationSingle != null) {
            disposables?.add(initialMarkerLocationSingle?.subscribe { next ->
                view?.setInitialMarkerLocation(next)
                initialMarkerLocationSingle = null
            })
        }
    }

    /**
     * this is used to start recording a route and displaying it on the map
     */
    fun initRouteRecordingObservable() {
        if (routeRecordingObservable == null) {
            isRecording = true
            view?.updateToolbarMenu()
            routeRecordingObservable = routeRecorder.getRecordingObservable().subscribeOn(Schedulers.io())
                    .map<List<LatLng>>(Function { next ->
                        val newList: MutableList<LatLng> = mutableListOf()
                        for (point in next) {
                            newList.add(LatLng(point.latitude, point.longitude))
                        }
                        newList
                    })
                    .observeOn(AndroidSchedulers.mainThread()).cache()
            startRouteRecording()
        }
    }

    private fun startRouteRecording() {
        if (routeRecordingObservable != null) {
            if (locationData == null) {
                locationData = mutableListOf()
            }
            view?.setPolylineData(locationData)
            disposables?.add(routeRecordingObservable?.subscribe { next ->
                locationData?.addAll(next)
                view?.setPolylineData(next)
            })
        }
    }

    fun saveRoute(title: String) {
        if (saveRouteCompletable == null) {
            saveRouteCompletable = apiService.saveRoute(locationData, title, routeRecorder.currentDistance)
            startSavingRoute()
        }
    }

    private fun startSavingRoute() {
        if (saveRouteCompletable != null) {
            view?.showProgressDialog()
            disposables?.add(saveRouteCompletable?.subscribe({
                // on complete
                view?.hideProgressDialog()
                view?.showSaveRouteSuccess()
                routeRecorder.currentDistance = 0f
                isRecording = false
                locationData = null
                view?.clearMapData()
                view?.updateToolbarMenu()
                saveRouteCompletable = null

            }, {
                // on error
                view?.hideProgressDialog()
                view?.showSaveRouteError()
                saveRouteCompletable = null
            }))
        }
    }

    override fun restoreSubscribersIfNeeded() {
        if (currentLocationSingle != null) {
            requestCurrentLocation()
        }

        if (routeRecordingObservable != null) {
            startRouteRecording()
        }

        if (initialMarkerLocationSingle != null) {
            requestInitialMarkerLocation()
        }

        if (saveRouteCompletable != null) {
            startSavingRoute()
        }
    }

}
