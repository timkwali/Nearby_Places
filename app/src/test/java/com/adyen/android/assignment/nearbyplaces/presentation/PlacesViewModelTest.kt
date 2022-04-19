package com.adyen.android.assignment.nearbyplaces.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adyen.android.assignment.common.domain.repository.FakePlacesRepository
import com.adyen.android.assignment.common.domain.repository.PlacesRepository
import com.adyen.android.assignment.nearbyplaces.domain.usecases.GetPlacesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PlacesViewModelTest {

    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var placesViewModel: PlacesViewModel
    private lateinit var getPlacesUseCase: GetPlacesUseCase
    private lateinit var placesRepository: PlacesRepository

    @Before
    fun setUp() {
        placesRepository = FakePlacesRepository()
        getPlacesUseCase = GetPlacesUseCase(placesRepository)
        placesViewModel = PlacesViewModel(getPlacesUseCase)
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
    fun `get places returns a stateflow of places resource`() = runTest {
        placesViewModel.getPlaces().join()

        val placesResource =  placesViewModel.placesList.first()

        Assert.assertEquals("test_name1", placesResource.data?.get(0)?.name)
    }

}