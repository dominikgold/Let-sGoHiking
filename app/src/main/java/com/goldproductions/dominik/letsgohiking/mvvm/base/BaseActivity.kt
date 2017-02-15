package com.goldproductions.dominik.letsgohiking.mvvm.base

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_map.*

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun initToolbar(@StringRes titleId: Int) {
        toolbar?.setTitle(titleId)
        setSupportActionBar(toolbar)
    }

}