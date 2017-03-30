package com.goldproductions.dominik.letsgohiking.mvp.routelist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.goldproductions.dominik.letsgohiking.R
import com.goldproductions.dominik.letsgohiking.mvp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_route_list.*

class RouteListActivity : BaseActivity<RouteListView, RouteListPresenter>(), RouteListView {

    companion object {
        fun launch(context: Context) {
            val intent: Intent = Intent(context, RouteListActivity::class.java)
            context.startActivity(intent)
        }
    }

    var layoutManager: LinearLayoutManager? = null
    var adapter: RouteListAdapter? = null

    override fun getPresenterInstance(): RouteListPresenter {
        return RouteListPresenter()
    }

    override fun getRetainFragmentTag(): String {
        return RouteListPresenter::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_list)
        initToolbar(R.string.my_routes)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun showLoadingState() {
        recycler_view.visibility = GONE
        loading_view.visibility = VISIBLE
    }

    override fun hideLoadingState() {
        recycler_view.visibility = VISIBLE
        loading_view.visibility = GONE
    }

    override fun showError() {
        Snackbar.make(recycler_view, R.string.route_list_not_loaded, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.route_list_try_again, {
                    getPresenter()?.loadRouteList()
                })
    }

    override fun showRouteList() {
        if (adapter == null) {
            layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recycler_view.layoutManager = layoutManager
            adapter = RouteListAdapter(getPresenter())
        }
        recycler_view.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        getPresenter()?.loadRouteList()
    }

    override fun onDestroy() {
        layoutManager = null
        adapter = null
        super.onDestroy()
    }
}