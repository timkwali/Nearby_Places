package com.adyen.android.assignment.nearbyplaces.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

import android.os.Looper
import android.util.Log

import com.google.android.gms.location.*


class LocationService(private val fragment: Fragment) {

    private val context = fragment.requireContext()
    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    var latitude = 0.0
    var longitude = 0.0

    fun isLocationOn(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun registerLocationPermission(grantedAction: () -> Unit, notGrantedAction: () -> Unit) {
        locationPermissionRequest = fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    grantedAction()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    grantedAction()
                }
                else -> {
                    notGrantedAction()
                }
            }
        }
    }

    fun requestLocationPermission() {
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }

    fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    }


    @SuppressLint("MissingPermission")
    fun getLastLocation(action: () -> Unit = {}) {
        fusedLocationClient.lastLocation
            .addOnCompleteListener { task ->
                val location = task.result
                if (location == null) {
                    requestNewLocationData { action() }
                } else {
                    latitude = location.latitude
                    longitude = location.longitude
                    action()
                }
            }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(action: () -> Unit = {}) {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback { action() },
            Looper.myLooper()!!
        )
    }

    private fun locationCallback(action: () -> Unit = {}): LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
            latitude = lastLocation.latitude
            longitude = lastLocation.longitude
            action()
        }
    }
}