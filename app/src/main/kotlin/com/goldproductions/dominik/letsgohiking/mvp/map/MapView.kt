package com.goldproductions.dominik.letsgohiking.mvp.map

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

interface MapView {
    fun initGoogleMap(googleMap: GoogleMap)

    fun setInitialLocation(position: LatLng)

    fun setInitialMarkerLocation(position: LatLng)

    fun setPolylineData(points: List<LatLng>?)

    fun setNewMarkerLocation(point: LatLng)

    fun updateToolbarMenu()

    fun clearMapData()

    fun showSaveRouteSuccess()

    fun showSaveRouteError()

    fun hideProgressDialog()

    fun showProgressDialog()
}