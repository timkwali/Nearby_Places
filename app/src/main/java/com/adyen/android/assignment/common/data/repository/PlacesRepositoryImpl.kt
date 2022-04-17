package com.adyen.android.assignment.common.data.repository

import androidx.room.withTransaction
import com.adyen.android.assignment.common.data.api.PlacesApi
import com.adyen.android.assignment.common.data.cache.PlacesDatabase
import com.adyen.android.assignment.common.data.cache.model.Place
import com.adyen.android.assignment.common.data.cache.model.PlaceItemMapper
import com.adyen.android.assignment.common.domain.repository.PlacesRepository
import com.adyen.android.assignment.common.utils.Resource
import com.adyen.android.assignment.common.utils.networkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlacesRepositoryImpl @Inject constructor(
    private val placesApi: PlacesApi,
    private val placesDatabase: PlacesDatabase
): PlacesRepository {

    private val placesDao = placesDatabase.placesDao()

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getNearbyPlaces(query: Map<String, String>): Flow<Resource<List<Place>>> = networkBoundResource(

        query = {
            placesDao.getAllPlaces()
        },
        fetch = {
            placesApi.getVenueRecommendations(query)
        },
        saveFetchResult = { responseWrapper ->
            val places = responseWrapper.results?.map { result ->
                PlaceItemMapper().mapToDomain(result)
            }

            placesDatabase.withTransaction {
                places?.let {
                    placesDao.clearAllPlaces()
                    placesDao.insertAllPlaces(places)
                }
            }
        }
    )

    override suspend fun getPlaceById(placeId: Int): Flow<Resource<Place>> = flow {
        try {
            val place = placesDao.getPlaceById(placeId)
            emit(Resource.Success(place))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error getting Place data!", null))
        }
    }
}