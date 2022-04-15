package com.adyen.android.assignment.common.data.api

import com.adyen.android.assignment.BuildConfig
import com.adyen.android.assignment.common.data.api.model.ResponseWrapper
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap


interface PlacesApi {
    /**
     * Get venue recommendations.
     *
     * See [the docs](https://developer.foursquare.com/reference/places-nearby)
     */
    @Headers("Authorization: ${BuildConfig.API_KEY}")
    @GET("places/nearby")
    suspend fun getVenueRecommendations(
        @QueryMap query: Map<String, String>,
    ): ResponseWrapper

//    companion object  {
//        private val retrofit by lazy {
//            Retrofit.Builder()
//                .baseUrl(BuildConfig.FOURSQUARE_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        }
//
//        val instance: PlacesApi by lazy { retrofit.create(PlacesApi::class.java) }
//    }
}
