package com.adyen.android.assignment.nearbyplaces.domain.usecases

import com.adyen.android.assignment.common.data.cache.model.Place
import com.adyen.android.assignment.common.domain.repository.PlacesRepository
import com.adyen.android.assignment.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlacesUseCase @Inject constructor(
    private val placesRepository: PlacesRepository
) {
    suspend operator fun invoke(query: Map<String, String>): Flow<Resource<List<Place>>> {
        return placesRepository.getNearbyPlaces(query)
    }
}