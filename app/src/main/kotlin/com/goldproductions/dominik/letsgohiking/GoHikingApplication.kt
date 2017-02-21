package com.goldproductions.dominik.letsgohiking

import android.app.Application
import com.goldproductions.dominik.letsgohiking.module.BaseComponent
import com.goldproductions.dominik.letsgohiking.module.BaseModule
import com.goldproductions.dominik.letsgohiking.module.DaggerBaseComponent

class GoHikingApplication: Application() {

    companion object {
        lateinit var graph: BaseComponent
    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerBaseComponent.builder().baseModule(BaseModule(this)).build()
    }
}