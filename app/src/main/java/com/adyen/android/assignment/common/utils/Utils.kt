package com.adyen.android.assignment.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.adyen.android.assignment.R
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

object Utils {

    fun hasInternetConnection(context: Context) : Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    fun ImageView.loadImage(url: String, placeHolder: Int = R.drawable.ic_baseline_apartment_24) {
        Glide.with(this.context)
            .load(url)
            .placeholder(placeHolder)
            .error(placeHolder)
            .into(this)
    }

    fun Fragment.showSnackBar(
        message: String?,
        duration: Int = 3000,
        view: View? = requireView()
    ) {
        Snackbar.make(view!!, message!!, duration).show()
    }
}