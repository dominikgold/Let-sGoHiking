package com.goldproductions.dominik.letsgohiking.mvp.routedetail

import com.goldproductions.dominik.letsgohiking.GoHikingApplication
import com.goldproductions.dominik.letsgohiking.mvp.base.BasePresenter
import com.goldproductions.dominik.letsgohiking.service.APIServiceIF
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RouteDetailPresenter : BasePresenter<RouteDetailView>() {
    init {
        GoHikingApplication.graph.inject(this)
    }

    @Inject
    lateinit var apiService: APIServiceIF

    private var loadLocationDataSingle: Single<List<LatLng>>? = null

    private var locationData: List<LatLng>? = null

    fun loadLocationData(routeId: Int) {
        if (loadLocationDataSingle == null && !hasLoaded()) {
            loadLocationDataSingle = apiService.getPointsForRoute(routeId).map<List<LatLng>>({ next ->
                val newList: MutableList<LatLng> = mutableListOf()
                next.mapTo(newList) { LatLng(it.latitude, it.longitude) }
                newList
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).cache()
            startLoadingLocationData()
        } else if (hasLoaded()) {
            view?.showMapWithPoints(locationData)
        }
    }

    private fun startLoadingLocationData() {
        if (loadLocationDataSingle != null) {
            view?.showLoadingState()
            disposables?.add(loadLocationDataSingle?.subscribe({ next ->
                locationData = next
                view?.hideLoadingState()
                view?.showMapWithPoints(locationData)
                loadLocationDataSingle = null
            }, { _ ->
                view?.hideLoadingState()
                view?.showError()
                loadLocationDataSingle = null
            }))
        }
    }

    private fun hasLoaded(): Boolean {
        return locationData != null
    }

    override fun restoreSubscribersIfNeeded() {
        if (loadLocationDataSingle != null) {
            startLoadingLocationData()
        }
    }

}