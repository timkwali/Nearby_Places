package com.adyen.android.assignment.nearbyplaces.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.common.data.api.VenueRecommendationsQueryBuilder
import com.adyen.android.assignment.common.data.cache.model.Place
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

    private var _placesList: MutableStateFlow<List<Place>> = MutableStateFlow(emptyList())
    private val placesList: StateFlow<List<Place>> get() = _placesList


    init {
        getPlaces()
    }

    fun getPlaces() = viewModelScope.launch {
        val query = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(52.36391, 4.8939)
            .build()

        getPlacesUseCase(query).collect {
            Log.d("ldfaf", it.toString())
        }
    }
}