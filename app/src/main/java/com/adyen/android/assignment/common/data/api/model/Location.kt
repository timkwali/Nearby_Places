package com.adyen.android.assignment.common.data.api.model

data class Location(
    val address: String?,
    val country: String?,
    val locality: String?,
    val neighbourhood: List<String>?,
    val postcode: String?,
    val region: String?,
)
