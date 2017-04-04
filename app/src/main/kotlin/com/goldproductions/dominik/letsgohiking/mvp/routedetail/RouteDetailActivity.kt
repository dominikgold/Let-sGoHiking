package com.goldproductions.dominik.letsgohiking.mvp.routedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import com.goldproductions.dominik.letsgohiking.R
import com.goldproductions.dominik.letsgohiking.model.Route
import com.goldproductions.dominik.letsgohiking.mvp.base.BaseActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_route_detail.*

class RouteDetailActivity : BaseActivity<RouteDetailView, RouteDetailPresenter>(), RouteDetailView {

    private val MAP_VIEW_SAVE_STATE = "map_view_save_state"

    private val LATLNG_BOUNDS_PADDING = 100

    companion object {

        private val EXTRA_ROUTE_TITLE: String = "extra_route_title"
        private val EXTRA_ROUTE_ID: String = "extra_route_id"

        fun getIntent(context: Context, route: Route): Intent {
            val intent: Intent = Intent(context, RouteDetailActivity::class.java)
            intent.putExtra(EXTRA_ROUTE_TITLE, route.title)
            intent.putExtra(EXTRA_ROUTE_ID, route.route_id)
            return intent
        }
    }

    private var googleMap: GoogleMap? = null

    override fun getPresenterInstance(): RouteDetailPresenter {
        return RouteDetailPresenter()
    }

    override fun getRetainFragmentTag(): String {
        return RouteDetailPresenter::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_detail)

        val extras: Bundle = intent.extras
        initToolbar(title = extras.getString(EXTRA_ROUTE_TITLE))

        val mapViewState: Bundle? = savedInstanceState?.getBundle(MAP_VIEW_SAVE_STATE)
        map_view.onCreate(mapViewState)
    }

    override fun onResume() {
        super.onResume()
        map_view.onResume()
        if (googleMap == null) {
            map_view.getMapAsync { googleMap: GoogleMap ->
                this.googleMap = googleMap
                getPresenter()?.loadLocationData(intent.extras.getInt(EXTRA_ROUTE_ID))
            }
        }
    }

    override fun onPause() {
        super.onPause()
        map_view.onPause()
    }

    override fun showLoadingState() {
        map_view.visibility = View.INVISIBLE
        loading_view.visibility = View.VISIBLE
    }

    override fun hideLoadingState() {
        map_view.visibility = View.VISIBLE
        loading_view.visibility = View.GONE
    }

    override fun showMapWithPoints(locationData: List<LatLng>?) {
        if (locationData == null) {
            return
        }
        googleMap?.addPolyline(PolylineOptions().addAll(locationData)
                .color(ContextCompat.getColor(this, R.color.moss_green)).width(8f))

        val startPoint = locationData.first()
        val endPoint = locationData.last()

        // set start point
        googleMap?.addMarker(MarkerOptions().position(startPoint)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))

        // set end point
        googleMap?.addMarker(MarkerOptions().position(endPoint)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))

        googleMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(
                LatLngBounds.Builder().include(startPoint).include(endPoint).build(), LATLNG_BOUNDS_PADDING))
    }

    override fun showError() {
        Snackbar.make(map_view, R.string.route_list_not_loaded, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.route_list_try_again, {
                    getPresenter()?.loadLocationData(intent.extras.getInt(EXTRA_ROUTE_ID))
                }).show()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        fixMapViewStateCrash(outState)
        super.onSaveInstanceState(outState)
    }

    /**
     * fixes map view causing a crash when restoring state in onCreate()
     * refer to: https://code.google.com/p/gmaps-api-issues/issues/detail?id=6237
     */
    private fun fixMapViewStateCrash(outState: Bundle?) {
        val mapViewState: Bundle = Bundle(outState)
        map_view.onSaveInstanceState(mapViewState)
        outState?.putBundle(MAP_VIEW_SAVE_STATE, mapViewState)
    }

    override fun onDestroy() {
        googleMap = null
        map_view.onDestroy()
        super.onDestroy()
    }
}