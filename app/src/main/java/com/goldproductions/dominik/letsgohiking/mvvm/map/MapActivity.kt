package com.goldproductions.dominik.letsgohiking.mvvm.map

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.goldproductions.dominik.letsgohiking.R
import com.goldproductions.dominik.letsgohiking.mvvm.base.BaseActivity
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : BaseActivity() {

    var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        initToolbar(R.string.activity_map_toolbar_title)

        map_view.onCreate(savedInstanceState)
        map_view.getMapAsync { googleMap: GoogleMap ->
            this.googleMap = googleMap
        }
    }

    override fun onResume() {
        map_view.onResume()
        super.onResume()
    }

    override fun onPause() {
        map_view.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        googleMap = null
        map_view.onDestroy()
        super.onDestroy()
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
}
