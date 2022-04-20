package com.adyen.android.assignment.common.domain.repository

import com.adyen.android.assignment.common.data.api.model.Category
import com.adyen.android.assignment.common.data.api.model.Icon
import com.adyen.android.assignment.common.data.api.model.Main
import com.adyen.android.assignment.common.data.cache.model.Place
import com.adyen.android.assignment.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakePlacesRepository: PlacesRepository {
    override suspend fun getNearbyPlaces(query: Map<String, String>): Flow<Resource<List<Place>>> {
        val testCoordinates = Main(0.0, 0.0)
        val testIcon = Icon("test_prefix", "test_suffix")
        val testCategories = listOf(Category(testIcon, "0", "test_icon_name"))
        val testPlacesList = listOf(
            Place(1, "test_name1", 5, "test_address", "test_timezone", testCoordinates, testCategories),
            Place(2, "test_name2", 5, "test_address", "test_timezone", testCoordinates, testCategories),
        )
        return flowOf(Resource.Success(testPlacesList))
    }

    override suspend fun getPlaceById(placeId: Int): Flow<Resource<Place>> {
        val testCoordinates = Main(0.0, 0.0)
        val testIcon = Icon("test_prefix", "test_suffix")
        val testCategories = listOf(Category(testIcon, "0", "test_icon_name"))
        val place = Place(1, "test_name1", 5, "test_address", "test_timezone", testCoordinates, testCategories)
        return flowOf(Resource.Success(place))
    }
}