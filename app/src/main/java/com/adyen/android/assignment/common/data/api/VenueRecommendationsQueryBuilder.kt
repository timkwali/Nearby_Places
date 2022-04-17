package com.adyen.android.assignment.common.data.api

class VenueRecommendationsQueryBuilder : PlacesQueryBuilder() {
    private var latitudeLongitude: String? = null
    private var limit: String? = null

    fun setLatitudeLongitude(latitude: Double, longitude: Double): VenueRecommendationsQueryBuilder {
        this.latitudeLongitude = "$latitude,$longitude"
        return this
    }

    fun setResponseLimit(responseLimit: String): VenueRecommendationsQueryBuilder {
        this.limit = responseLimit
        return this
    }

    override fun putQueryParams(queryParams: MutableMap<String, String>) {
        latitudeLongitude?.apply { queryParams["ll"] = this }
        limit?.apply { queryParams["limit"] = this }
    }
}
