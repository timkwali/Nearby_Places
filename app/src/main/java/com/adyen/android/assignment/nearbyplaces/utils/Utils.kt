package com.adyen.android.assignment.nearbyplaces.utils

import android.app.Activity
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog

object Utils {

    fun showAlertDialog(
        activity: Activity,
        title: String,
        message: String,
        tag: Int,
        dialogHelper: DialogHelper
    ) {
        try {
            val builder: AlertDialog.Builder? = if (activity.parent != null) AlertDialog.Builder(activity.parent)
            else AlertDialog.Builder(activity)
            builder?.setCancelable(false)
            builder?.setTitle(title)
            builder?.setMessage(message)
            builder?.setPositiveButton("Ok") { dialog, which ->
                dialog.dismiss()
//                Runtime.getRuntime().gc()
                dialogHelper.onOkClick(tag)
            }
            builder?.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
//                Runtime.getRuntime().gc()
                dialogHelper.onCancelClick(tag)
            }
            val msg = builder?.create()
            msg?.show()
        } catch (ex: Exception) {
            Log.d("dialogError", ex.message.toString())
        }
    }
}