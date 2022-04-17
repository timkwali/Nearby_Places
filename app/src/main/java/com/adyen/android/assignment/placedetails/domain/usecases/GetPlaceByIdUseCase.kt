package com.adyen.android.assignment.placedetails.domain.usecases

import com.adyen.android.assignment.common.data.cache.model.Place
import com.adyen.android.assignment.common.domain.repository.PlacesRepository
import com.adyen.android.assignment.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlaceByIdUseCase @Inject constructor(
    private val placesRepository: PlacesRepository
) {
    suspend operator fun invoke(placeId: Int): Flow<Resource<Place>> {
        return placesRepository.getPlaceById(placeId)
    }
}