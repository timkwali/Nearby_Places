package com.adyen.android.assignment.common.data.api.model

data class GeoCode(
    val main: Main?,
    val roof: Roof
)

data class Main(
    val latitude: Double?,
    val longitude: Double?,
)

data class Roof(
    val latitude: Double?,
    val longitude: Double?,
)