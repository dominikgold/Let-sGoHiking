package com.goldproductions.dominik.letsgohiking.mvvm.routelist

import android.os.Bundle
import com.goldproductions.dominik.letsgohiking.R
import com.goldproductions.dominik.letsgohiking.mvvm.base.BaseActivity

class RouteListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_list)
        initToolbar(R.string.my_routes)
    }
}