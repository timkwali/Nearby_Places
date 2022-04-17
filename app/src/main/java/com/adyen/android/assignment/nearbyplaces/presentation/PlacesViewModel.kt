package com.adyen.android.assignment.nearbyplaces.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.common.data.api.VenueRecommendationsQueryBuilder
import com.adyen.android.assignment.common.data.cache.model.Place
import com.adyen.android.assignment.common.utils.Resource
import com.adyen.android.assignment.nearbyplaces.domain.usecases.GetPlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val getPlacesUseCase: GetPlacesUseCase
): ViewModel() {

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    private var _placesList: MutableStateFlow<Resource<List<Place>>> =
        MutableStateFlow(Resource.Loading(emptyList()))
    val placesList: StateFlow<Resource<List<Place>>> get() = _placesList

    var pendingScrollToTopAfterRefresh = false

    init {
        getPlaces()
    }

    fun getPlaces() = viewModelScope.launch {
        val query = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(52.36391, 4.8939)
            .setResponseLimit("50")
            .build()

        getPlacesUseCase(query).collect {
            _placesList.value = it
        }
    }
}