package com.goldproductions.dominik.letsgohiking.mvp.routelist

interface RouteListView {
    fun showLoadingState()

    fun showRouteList()

    fun hideLoadingState()

    fun showError()
}