package com.goldproductions.dominik.letsgohiking.mvp.base

import android.os.Bundle
import android.support.v4.app.Fragment

class BaseRetainFragment<P> : Fragment() {

    var presenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

}