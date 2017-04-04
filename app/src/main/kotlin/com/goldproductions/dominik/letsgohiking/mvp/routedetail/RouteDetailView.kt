package com.goldproductions.dominik.letsgohiking.mvp.routedetail

import com.google.android.gms.maps.model.LatLng

interface RouteDetailView {
    fun showLoadingState()

    fun hideLoadingState()

    fun showMapWithPoints(locationData: List<LatLng>?)

    fun showError()
}