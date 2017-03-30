package com.goldproductions.dominik.letsgohiking.mvp.map

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.goldproductions.dominik.letsgohiking.R
import kotlinx.android.synthetic.main.save_route_dialog.*

class SaveRouteDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater: LayoutInflater = activity.layoutInflater
        return builder.setView(inflater.inflate(R.layout.save_route_dialog, null)).create()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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