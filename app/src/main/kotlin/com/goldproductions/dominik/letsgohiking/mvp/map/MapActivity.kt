package com.goldproductions.dominik.letsgohiking.mvp.map

import android.app.ProgressDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.goldproductions.dominik.letsgohiking.R
import com.goldproductions.dominik.letsgohiking.mvp.base.BaseActivity
import com.goldproductions.dominik.letsgohiking.mvp.routelist.RouteListActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : BaseActivity<MapView, MapPresenter>(), MapView {

    private val INITIAL_ZOOM_LEVEL = 15f
    private val MAP_VIEW_SAVE_STATE = "map_view_save_state"

    private var googleMap: GoogleMap? = null

    private var polyline: Polyline? = null

    private var marker: Marker? = null

    private var progressDialog: ProgressDialog? = null

    override fun getPresenterInstance(): MapPresenter {
        return MapPresenter()
    }

    override fun getRetainFragmentTag(): String {
        return MapPresenter::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        initToolbar(R.string.activity_map_toolbar_title)

        val mapViewState: Bundle? = savedInstanceState?.getBundle(MAP_VIEW_SAVE_STATE)
        map_view.onCreate(mapViewState)
        map_view.getMapAsync { googleMap: GoogleMap ->
            initGoogleMap(googleMap)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_map_activity, menu)
        if (getPresenter()?.isRecording ?: false) {
            menuInflater.inflate(R.menu.menu_save_route, menu)
        } else {
            menuInflater.inflate(R.menu.menu_start_route, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item?.itemId
        if (itemId == R.id.menu_my_routes) {
            RouteListActivity.launch(this)
            return true
        } else if (itemId == R.id.menu_licences) {
//            TODO
            return true
        } else if (itemId == R.id.menu_start) {
            getPresenter()?.initMarkerLocationSingle()
            getPresenter()?.initRouteRecordingObservable()
            return true
        } else if (itemId == R.id.menu_save_route) {
            launchSaveDialog()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun launchSaveDialog() {
        val dialog: SaveRouteDialog = SaveRouteDialog()
        dialog.show(fragmentManager, SaveRouteDialog::class.java.name)
    }

    fun saveRoute(title: String) {
        getPresenter()?.saveRoute(title)
    }

    override fun updateToolbarMenu() {
        invalidateOptionsMenu()
    }

    override fun initGoogleMap(googleMap: GoogleMap) {
        this.googleMap = googleMap
        polyline = googleMap.addPolyline(PolylineOptions().color(ContextCompat.getColor(this, R.color.moss_green))
                .width(8f))
        getPresenter()?.initCurrentLocationSingle()
    }

    override fun setInitialLocation(position: LatLng) {
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(position, INITIAL_ZOOM_LEVEL))
    }

    override fun setInitialMarkerLocation(position: LatLng) {
        marker = googleMap?.addMarker(MarkerOptions().position(position))
    }

    override fun setPolylineData(points: List<LatLng>?) {
        polyline?.points = points
    }

    override fun setNewMarkerLocation(point: LatLng) {
        marker?.position = point
    }

    override fun clearMapData() {
        polyline = null
        marker = null
    }

    /**
     * called when saving a route. displays a loading spinner that blocks the ui.
     */
    override fun showProgressDialog() {
        progressDialog = ProgressDialog.show(this, getString(R.string.saving_route), getString(R.string.please_wait))
    }

    override fun hideProgressDialog() {
        progressDialog?.dismiss()
    }

    override fun showSaveRouteError() {
        Snackbar.make(map_view, R.string.save_route_success, Snackbar.LENGTH_SHORT).show()
    }

    override fun showSaveRouteSuccess() {
        Snackbar.make(map_view, R.string.save_route_error, Snackbar.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        map_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        map_view.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        fixMapViewSateCrash(outState)
        super.onSaveInstanceState(outState)
    }

    /**
     * fixes map view causing a crash when restoring state in onCreate()
     * refer to: https://code.google.com/p/gmaps-api-issues/issues/detail?id=6237
     */
    private fun fixMapViewSateCrash(outState: Bundle?) {
        val mapViewState: Bundle = Bundle(outState)
        map_view.onSaveInstanceState(mapViewState)
        outState?.putBundle(MAP_VIEW_SAVE_STATE, mapViewState)
    }

    override fun onDestroy() {
        googleMap = null
        polyline = null
        marker = null
        progressDialog = null
        map_view.onDestroy()
        super.onDestroy()
    }
}