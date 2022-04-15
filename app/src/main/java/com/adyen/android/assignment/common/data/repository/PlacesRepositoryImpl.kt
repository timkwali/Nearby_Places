package com.adyen.android.assignment.common.data.repository

import com.adyen.android.assignment.common.data.api.PlacesApi
import com.adyen.android.assignment.common.data.cache.PlacesDatabase
import com.adyen.android.assignment.common.data.cache.model.Place
import com.adyen.android.assignment.common.data.cache.model.PlaceItemMapper
import com.adyen.android.assignment.common.domain.repository.PlacesRepository
import com.adyen.android.assignment.common.utils.Resource
import com.adyen.android.assignment.common.utils.networkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class PlacesRepositoryImpl @Inject constructor(
    private val placesApi: PlacesApi,
    private val placesDatabase: PlacesDatabase
): PlacesRepository {

    private val placesDao = placesDatabase.placesDao()

//    @OptIn(ExperimentalCoroutinesApi::class)
//    override suspend fun getNearbyPlaces(query: Map<String, String>):
//            Flow<Resource<List<Place>>> = networkBoundResource(
//
//        query = {
//
//        },
//        fetch = {
//
//        },
//        saveFetchResult = {
//
//        },
//        shouldFetch = {
//
//        },
//        onFetchSuccess = {
//
//        },
//        onFetchFailed = {
//
//        }
//    )

//    override suspend fun getNearbyPlaces(query: Map<String, String>): Flow<Resource<List<Place>>> {
//        return flowOf(
//            placesApi.getVenueRecommendations(query).results
//        )
//    }
}