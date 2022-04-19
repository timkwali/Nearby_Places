package com.adyen.android.assignment.common.utils

import com.adyen.android.assignment.common.data.api.PlacesApi
import com.adyen.android.assignment.common.data.api.model.*

class FakePlacesApi: PlacesApi {
    override suspend fun getVenueRecommendations(query: Map<String, String>): ResponseWrapper {
        val location = Location("address", "country", "locality", listOf("neighbourhood"), "postcode", "region")
        val result = Result(
            emptyList(),
            50,
            GeoCode(Main(0.0, 0.0)),
            location,
            "name",
            "timezone"
        )

        return ResponseWrapper(
            listOf(result)
        )
    }
}