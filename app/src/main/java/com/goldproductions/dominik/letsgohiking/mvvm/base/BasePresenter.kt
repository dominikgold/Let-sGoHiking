package com.goldproductions.dominik.letsgohiking.mvvm.base

class BasePresenter<V> : BasePresenterIF<V> {

    var view: V? = null

    override fun onResume(view: V) {
        this.view = view
    }

    override fun onPause() {
        this.view = null
    }
}