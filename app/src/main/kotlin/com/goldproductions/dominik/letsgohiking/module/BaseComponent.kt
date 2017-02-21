package com.goldproductions.dominik.letsgohiking.module

import com.goldproductions.dominik.letsgohiking.mvp.map.MapPresenter
import com.goldproductions.dominik.letsgohiking.mvp.routelist.RouteListPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(BaseModule::class))
interface BaseComponent {
    fun inject(mapPresenter: MapPresenter)

    fun inject(routeListPresenter: RouteListPresenter)
}