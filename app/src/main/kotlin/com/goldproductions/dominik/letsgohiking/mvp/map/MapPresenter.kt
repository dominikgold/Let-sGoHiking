package com.goldproductions.dominik.letsgohiking.mvp.map

import com.goldproductions.dominik.letsgohiking.GoHikingApplication
import com.goldproductions.dominik.letsgohiking.mvp.base.BasePresenter
import com.goldproductions.dominik.letsgohiking.service.APIServiceIF
import com.goldproductions.dominik.letsgohiking.service.RouteRecorderIF
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapPresenter() : BasePresenter<MapView>() {

    init {
        GoHikingApplication.graph.inject(this)
    }

    @Inject
    lateinit var routeRecorder: RouteRecorderIF

    @Inject
    lateinit var apiService: APIServiceIF

    private var routeRecordingObservable: Observable<LatLng>? = null

    private var currentLocationSingle: Single<LatLng>? = null

    private var saveRouteCompletable: Completable? = null

    private var locationData: MutableList<LatLng>? = null

    var isRecording: Boolean = false

    /**
     * this is used to get the initial location of the user on app startup to position the map camera
     */
    fun initCurrentLocationSingle() {
        if (currentLocationSingle == null) {
            currentLocationSingle = routeRecorder.getCurrentLocation()
                .map<LatLng>(Function { next->
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
     * this is used to start recording a route
     */
    fun initRouteRecordingObservable() {
        if (routeRecordingObservable == null) {
            isRecording = true
            view?.updateToolbarMenu()
            routeRecordingObservable = routeRecorder.getRecordingObservable()
                    .map<LatLng>(Function { next ->
                        LatLng(next.latitude, next.longitude)
                    }).cache()
            startRouteRecording()
        }
    }

    private fun startRouteRecording() {
        if (routeRecordingObservable != null) {
            if (locationData == null) {
                locationData = mutableListOf()
            }
            disposables?.add(routeRecordingObservable?.subscribe { next ->
                locationData?.add(next)
                view?.setPolylineData(locationData)
                setNewMarkerLocation()
            })
        }
    }

    private fun setNewMarkerLocation() {
        val newMarkerLocation = locationData?.last()
        if (newMarkerLocation != null) {
            view?.setMarkerLocation(newMarkerLocation)
        }
    }

    fun saveRoute(title: String) {
        if (saveRouteCompletable == null) {
            saveRouteCompletable = apiService.saveRoute(locationData, title,
                                        routeRecorder.getDistance())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
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
                routeRecorder.resetRouteRecording()
                isRecording = false
                locationData = null
                view?.clearMapData()
                view?.updateToolbarMenu()
                routeRecordingObservable = null
                saveRouteCompletable = null

            }, {
                // on error
                view?.hideProgressDialog()
                view?.showSaveRouteError()
                saveRouteCompletable = null
            }))
        }
    }

    fun initMapData() {
        if (isRecording && locationData?.size ?: 0 > 0) {
            view?.setPolylineData(locationData)
            setNewMarkerLocation()
        } else {
            initCurrentLocationSingle()
        }
    }

    override fun restoreSubscribersIfNeeded() {
        if (saveRouteCompletable != null) {
            startSavingRoute()
        }

        if (routeRecordingObservable != null) {
            startRouteRecording()
        }

        if (currentLocationSingle != null) {
            requestCurrentLocation()
        }
    }

}
