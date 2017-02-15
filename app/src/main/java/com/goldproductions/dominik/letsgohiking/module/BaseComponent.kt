package com.goldproductions.dominik.letsgohiking.module

import com.goldproductions.dominik.letsgohiking.mvvm.map.MapViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(BaseModule::class))
interface BaseComponent {
    fun inject(mapViewModel: MapViewModel)
}