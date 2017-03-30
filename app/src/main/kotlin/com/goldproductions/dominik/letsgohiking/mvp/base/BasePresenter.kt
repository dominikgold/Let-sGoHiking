package com.goldproductions.dominik.letsgohiking.mvp.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V> {

    protected var view: V? = null
    protected var disposables: CompositeDisposable? = null

    fun onResume(view: V) {
        this.view = view
        disposables = CompositeDisposable()
        restoreSubscribersIfNeeded()
    }

    fun onPause() {
        this.view = null
        disposables?.dispose()
    }

    abstract fun restoreSubscribersIfNeeded()

}