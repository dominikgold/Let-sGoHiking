package com.goldproductions.dominik.letsgohiking.mvp.routelist

import com.goldproductions.dominik.letsgohiking.model.Route

interface RouteListView {
    fun showLoadingState()

    fun showRouteList()

    fun hideLoadingState()

    fun showError()

    fun openRouteDetail(route: Route)
}