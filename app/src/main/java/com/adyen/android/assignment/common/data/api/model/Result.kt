package com.adyen.android.assignment.common.data.api.model

data class Result(
    val categories: List<Category>?,
    val distance: Int?,
    val geocodes: GeoCode?,
    val location: Location?,
    val name: String?,
    val timezone: String?,
)