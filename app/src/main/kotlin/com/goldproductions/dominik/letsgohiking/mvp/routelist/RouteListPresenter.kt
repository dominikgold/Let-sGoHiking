package com.goldproductions.dominik.letsgohiking.mvp.routelist

import com.goldproductions.dominik.letsgohiking.GoHikingApplication
import com.goldproductions.dominik.letsgohiking.model.Route
import com.goldproductions.dominik.letsgohiking.mvp.base.BasePresenter
import com.goldproductions.dominik.letsgohiking.service.APIServiceIF
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RouteListPresenter() : BasePresenter<RouteListView>() {

    init {
        GoHikingApplication.graph.inject(this)
    }

    @Inject
    lateinit var apiService: APIServiceIF

    var routes: List<Route>? = null

    private var loadRouteListSingle: Single<List<Route>>? = null

    fun getItemCount(): Int {
        return routes?.size ?: 0
    }

    /**
     * inits the Single that loads the routes when it has not been created yet.
     * when the route list was already loaded before, simply tells the view to display the route list
     */
    fun loadRouteList() {
        if (hasLoaded()) {
            view?.showRouteList()
        } else if (loadRouteListSingle == null) {
            loadRouteListSingle = apiService.getRoutes().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).cache()
            startRouteListLoading()
        }
    }

    private fun hasLoaded(): Boolean {
        return routes != null
    }

    /**
     * subscribes to the load route list Single to start the loading.
     */
    private fun startRouteListLoading() {
        if (loadRouteListSingle != null) {
            view?.showLoadingState()
            disposables?.add(loadRouteListSingle?.subscribe({ next ->
                routes = next
                view?.hideLoadingState()
                view?.showRouteList()
                loadRouteListSingle = null
            }, {
                view?.hideLoadingState()
                view?.showError()
                loadRouteListSingle = null
            }))
        }
    }

    fun openRouteDetail(route: Route?) {
        if (route != null) {
            view?.openRouteDetail(route)
        }
    }

    override fun restoreSubscribersIfNeeded() {
        if (loadRouteListSingle != null) {
            startRouteListLoading()
        }
    }

}