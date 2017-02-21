package com.goldproductions.dominik.letsgohiking.mvp.routelist

import com.goldproductions.dominik.letsgohiking.GoHikingApplication
import com.goldproductions.dominik.letsgohiking.model.Route
import com.goldproductions.dominik.letsgohiking.mvp.base.BasePresenter
import com.goldproductions.dominik.letsgohiking.service.APIService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RouteListPresenter() : BasePresenter<RouteListView>() {
    init {
        GoHikingApplication.graph.inject(this)
    }

    @Inject
    lateinit var apiService: APIService

    var routes: List<Route>? = null

    private var loadRouteListSingle: Single<List<Route>>? = null

    fun getItemCount(): Int {
        return routes?.size ?: 0
    }

    /**
     * inits the Single that loads the routes when it has not been created yet. when the route list was already loaded
     * before, simply tells to view to display the route list
     */
    fun loadRouteList() {
        if (loadRouteListSingle == null && !hasLoaded()) {
            loadRouteListSingle = apiService.getRoutes().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).cache()
            startRouteListLoading()
        } else if (hasLoaded()) {
            view?.showRouteList()
        }
    }

    private fun hasLoaded(): Boolean {
        return routes != null
    }

    /**
     * subscribes to the load route list Single to start the loading.
     */
    private fun startRouteListLoading() {
        view?.showLoadingState()
        disposables?.add(loadRouteListSingle?.subscribe({ next ->
            routes = next
            view?.hideLoadingState()
            view?.showRouteList()
            loadRouteListSingle = null
        }, { error ->
            view?.hideLoadingState()
            view?.showError()
            loadRouteListSingle = null
        }))
    }

    fun openRouteDetail(route: Route?) {
        if (route != null) {

        }
    }

    override fun restoreSubscribersIfNeeded() {
        if (loadRouteListSingle != null) {
            startRouteListLoading()
        }
    }

}