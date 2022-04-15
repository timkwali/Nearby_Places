package com.adyen.android.assignment.common.data.api.model

data class GeoCode(
    val main: Main?
)

data class Main(
    val latitude: Double?,
    val longitude: Double?,
)