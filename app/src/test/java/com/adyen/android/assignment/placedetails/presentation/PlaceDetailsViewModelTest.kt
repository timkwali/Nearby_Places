package com.adyen.android.assignment.placedetails.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adyen.android.assignment.common.domain.repository.FakePlacesRepository
import com.adyen.android.assignment.common.domain.repository.PlacesRepository
import com.adyen.android.assignment.nearbyplaces.domain.usecases.GetPlacesUseCase
import com.adyen.android.assignment.nearbyplaces.presentation.PlacesViewModel
import com.adyen.android.assignment.placedetails.domain.usecases.GetPlaceByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PlaceDetailsViewModelTest {

    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var placeDetailsViewModel: PlaceDetailsViewModel
    private lateinit var getPlaceByIdUseCase: GetPlaceByIdUseCase
    private lateinit var placesRepository: PlacesRepository

    @Before
    fun setUp() {
        placesRepository = FakePlacesRepository()
        getPlaceByIdUseCase = GetPlaceByIdUseCase(placesRepository)
        placeDetailsViewModel = PlaceDetailsViewModel(getPlaceByIdUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get place by id returns a stateflow of place resource`() = runTest {
        placeDetailsViewModel.getPlaceById().join()

        val placesResource =  placeDetailsViewModel.place.first()

        assertEquals("test_name1", placesResource.data?.name)
    }
}