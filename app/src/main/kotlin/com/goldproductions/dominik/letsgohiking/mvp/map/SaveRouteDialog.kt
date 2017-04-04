package com.goldproductions.dominik.letsgohiking.mvp.map

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goldproductions.dominik.letsgohiking.R
import kotlinx.android.synthetic.main.save_route_dialog.*

class SaveRouteDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.save_route_dialog, container, false)
                ?: super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        dialog_save_route.setOnClickListener {
            saveRouteAndClose()
        }

        dialog_cancel.setOnClickListener {
            dismiss()
        }
    }

    private fun saveRouteAndClose() {
        val title = save_route_enter_title.text.toString()
        if (!title.isEmpty() && activity is MapActivity) {
            val mapActivity = activity as MapActivity
            mapActivity.saveRoute(title)
            dismiss()
        }
    }

}