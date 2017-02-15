package com.goldproductions.dominik.letsgohiking.mvvm.map

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.goldproductions.dominik.letsgohiking.R
import com.goldproductions.dominik.letsgohiking.model.GPSPoint
import com.goldproductions.dominik.letsgohiking.mvvm.base.BaseActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : BaseActivity(), MapViewIF {

    private val INITIAL_ZOOM_LEVEL = 15f

    private var googleMap: GoogleMap? = null

    private var polyline: Polyline? = null

    lateinit private var presenter: MapPresenterIF

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        initToolbar(R.string.activity_map_toolbar_title)
        presenter = MapPresenter()

        map_view.onCreate(savedInstanceState)
        map_view.getMapAsync { googleMap: GoogleMap ->
            this.googleMap = googleMap
            presenter.requestInitialLocation()
            polyline = googleMap.addPolyline(PolylineOptions())
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
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setInitialLocation(next: GPSPoint?) {
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(next?.latitude!!, next?.longitude!!),
                INITIAL_ZOOM_LEVEL))
    }

    override fun addToPolyline(points: List<GPSPoint>) {
        val polylinePoints: MutableList<LatLng>? = polyline?.points
        for (point in points) {
            polylinePoints?.add(LatLng(point.longitude, point.latitude))
        }
        polyline?.points = polylinePoints
    }

    override fun onResume() {
        map_view.onResume()
        presenter.onResume(this)
        super.onResume()
    }

    override fun onPause() {
        map_view.onPause()
        presenter.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        googleMap = null
        map_view.onDestroy()
        super.onDestroy()
    }
}
