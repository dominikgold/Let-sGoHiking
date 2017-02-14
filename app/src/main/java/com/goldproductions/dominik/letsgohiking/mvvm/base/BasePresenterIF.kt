package com.goldproductions.dominik.letsgohiking.mvvm.base

interface BasePresenterIF<V> {
    fun onResume(view: V)

    fun onPause()
}