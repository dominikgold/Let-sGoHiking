package com.goldproductions.dominik.letsgohiking.mvp.map

import com.google.android.gms.maps.model.LatLng

interface MapView {
    fun setInitialLocation(position: LatLng)

    fun setMarkerLocation(position: LatLng)

    fun setPolylineData(points: List<LatLng>?)

    fun updateToolbarMenu()

    fun clearMapData()

    fun showSaveRouteSuccess()

    fun showSaveRouteError()

    fun hideProgressDialog()

    fun showProgressDialog()
}