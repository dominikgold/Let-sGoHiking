package com.goldproductions.dominik.letsgohiking.mvp.base

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_map.*

abstract class BaseActivity<V, P: BasePresenter<V>> : AppCompatActivity() {

    var retainFragment: BaseRetainFragment<P>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (retainFragment == null) {
            initRetainFragment()
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onResume() {
        super.onResume()
        // unchecked cast but passing the generic from the subclass guarantees that we have the right type
        getPresenter()?.onResume(this as V)
    }

    override fun onPause() {
        super.onPause()
        getPresenter()?.onPause()
    }

    @Suppress("UNCHECKED_CAST")
    private fun initRetainFragment() {
        val fragmentManager: FragmentManager = supportFragmentManager
        // unchecked cast but since we have the unique tag the right type is guaranteed
        retainFragment = fragmentManager.findFragmentByTag(getRetainFragmentTag()) as? BaseRetainFragment<P>
        if (retainFragment == null) {
            retainFragment = BaseRetainFragment()
            retainFragment?.presenter = getPresenterInstance()
            fragmentManager.beginTransaction().add(retainFragment, getRetainFragmentTag()).commit()
        }
    }

    fun initToolbar(@StringRes titleId: Int) {
        toolbar?.setTitle(titleId)
        setSupportActionBar(toolbar)
    }

    fun getPresenter(): P? {
        if (retainFragment == null) {
            initRetainFragment()
        }
        return retainFragment?.presenter
    }

    abstract fun getPresenterInstance(): P

    abstract fun getRetainFragmentTag(): String

}