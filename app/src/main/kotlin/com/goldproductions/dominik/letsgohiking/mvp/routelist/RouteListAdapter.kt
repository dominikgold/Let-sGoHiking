package com.goldproductions.dominik.letsgohiking.mvp.routelist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.goldproductions.dominik.letsgohiking.R

class RouteListAdapter(val presenter: RouteListPresenter?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    init {
        setHasStableIds(true)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is RouteListItemHolder) {
            holder.bind(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent?.context)
        return RouteListItemHolder(inflater.inflate(R.layout.route_list_item, parent, false), presenter)
    }

    override fun getItemCount(): Int {
        return presenter?.getItemCount() ?: 0
    }

}