package com.adyen.android.assignment.common.domain.repository

import com.adyen.android.assignment.common.data.cache.model.Place
import com.adyen.android.assignment.common.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PlacesRepository {

    suspend fun getNearbyPlaces(query: Map<String, String>): Flow<Resource<List<Place>>>
}