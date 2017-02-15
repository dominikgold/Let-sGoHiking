package com.goldproductions.dominik.letsgohiking.mvvm.map

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.goldproductions.dominik.letsgohiking.R
import com.goldproductions.dominik.letsgohiking.model.GPSPoint
import com.goldproductions.dominik.letsgohiking.mvvm.base.BaseActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : BaseActivity() {

    private val INITIAL_ZOOM_LEVEL = 15f

    private var googleMap: GoogleMap? = null

    private var polyline: Polyline? = null

    private var marker: Marker? = null

    lateinit private var viewModel: MapViewModelIF

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        initToolbar(R.string.activity_map_toolbar_title)
        viewModel = MapViewModel()

        map_view.onCreate(savedInstanceState)
        map_view.getMapAsync { googleMap: GoogleMap ->
            initGoogleMap(googleMap)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_map_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item?.itemId
        if (itemId == R.id.menu_my_routes) {
//            TODO
            return true
        } else if (itemId == R.id.menu_licences) {
//            TODO
            return true
        } else if (itemId == R.id.menu_start) {
            setInitialMarkerLocation()
            startRouteRecording()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initGoogleMap(googleMap: GoogleMap) {
        this.googleMap = googleMap
        setInitialLocation()
        polyline = googleMap.addPolyline(PolylineOptions().color(ContextCompat.getColor(this, R.color.moss_green))
                .width(8f))
    }

    private fun setInitialLocation() {
        viewModel.requestCurrentLocation().subscribe { next ->
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(next.latitude, next.longitude),
                    INITIAL_ZOOM_LEVEL))
        }
    }

    private fun setInitialMarkerLocation() {
        viewModel.requestCurrentLocation().subscribe { next ->
            marker = googleMap?.addMarker(MarkerOptions().position(LatLng(next.latitude, next.longitude)))
        }
    }

    private fun startRouteRecording() {
        viewModel.startRouteRecording().subscribe { next ->
            addToPolyline(next)
            setNewMarkerLocation(next.last())
        }
    }

    private fun addToPolyline(points: List<GPSPoint>) {
        val polylinePoints: MutableList<LatLng>? = polyline?.points
        for (point in points) {
            polylinePoints?.add(LatLng(point.latitude, point.longitude))
        }
        polyline?.points = polylinePoints
    }

    private fun setNewMarkerLocation(point: GPSPoint) {
        marker?.position = LatLng(point.latitude, point.longitude)
    }

    override fun onResume() {
        super.onResume()
        map_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        map_view.onPause()
    }

    override fun onDestroy() {
        googleMap = null
        map_view.onDestroy()
        super.onDestroy()
    }
}
