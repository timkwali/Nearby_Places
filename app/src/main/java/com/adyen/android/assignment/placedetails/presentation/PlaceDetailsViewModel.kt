package com.adyen.android.assignment.placedetails.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.common.data.cache.model.Place
import com.adyen.android.assignment.common.utils.Resource
import com.adyen.android.assignment.placedetails.domain.usecases.GetPlaceByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceDetailsViewModel @Inject constructor(
    private val getPlaceByIdUseCase: GetPlaceByIdUseCase
): ViewModel() {

    var placeId: Int = 0

    private var _place: MutableStateFlow<Resource<Place>> = MutableStateFlow(Resource.Loading())
    val place: StateFlow<Resource<Place>> get() = _place

    fun getPlaceById() = viewModelScope.launch {
        _place.value = Resource.Loading()
        delay(500)
        getPlaceByIdUseCase(placeId).collect {
            _place.value = it
        }
    }
}