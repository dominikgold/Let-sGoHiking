package com.goldproductions.dominik.letsgohiking.mvp.routelist

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.GONE
import com.goldproductions.dominik.letsgohiking.model.Route
import com.goldproductions.dominik.letsgohiking.utility.getFormattedDistanceString
import kotlinx.android.synthetic.main.route_list_item.view.*

class RouteListItemHolder(view: View, val presenter: RouteListPresenter?) : RecyclerView.ViewHolder(view) {
    init {
        itemView.setOnClickListener({
            presenter?.openRouteDetail(route)
        })
    }

    private var route: Route? = null

    fun bind(position: Int) {
        route = presenter?.routes?.get(position)
        if (route != null) {
            itemView.route_list_item_title.text = route?.title
            itemView.route_list_item_distance.text = getFormattedDistanceString(route?.distance)
        } else {
            itemView.visibility = GONE
        }
    }

}