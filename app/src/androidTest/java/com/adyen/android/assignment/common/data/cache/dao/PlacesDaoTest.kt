package com.adyen.android.assignment.common.data.cache.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.adyen.android.assignment.common.data.api.model.Category
import com.adyen.android.assignment.common.data.api.model.Icon
import com.adyen.android.assignment.common.data.api.model.Main
import com.adyen.android.assignment.common.data.cache.PlacesDatabase
import com.adyen.android.assignment.common.data.cache.model.Place
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class PlacesDaoTest {
    private lateinit var placesDatabase: PlacesDatabase
    private lateinit var placesDao: PlacesDao

    @Before
    fun setUp() {
        placesDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PlacesDatabase::class.java,
        ).allowMainThreadQueries().build()
        placesDao = placesDatabase.placesDao()
    }

    @After
    fun tearDown() {
        placesDatabase.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun insertAllPlacesTest() = runTest {
        val testCoordinates = Main(0.0, 0.0)
        val testIcon = Icon("test_prefix", "test_suffix")
        val testCategories = listOf(Category(testIcon, "0", "test_icon_name"))
        val testPlacesList = listOf(
            Place(1, "test_name1", 5.0, "test_address", "test_timezone", testCoordinates, testCategories),
            Place(2, "test_name2", 5.0, "test_address", "test_timezone", testCoordinates, testCategories),
        )

        placesDao.insertAllPlaces(testPlacesList)

        val placesListResult = placesDao.getAllPlaces().first()

        Assert.assertEquals("test_name1", placesListResult[0].name)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getPlaceByIdTest() = runTest {
        val testCoordinates = Main(0.0, 0.0)
        val testIcon = Icon("test_prefix", "test_suffix")
        val testCategories = listOf(Category(testIcon, "0", "test_icon_name"))
        val testPlacesList = listOf(
            Place(1, "test_name1", 5.0, "test_address", "test_timezone", testCoordinates, testCategories),
            Place(2, "test_name2", 5.0, "test_address", "test_timezone", testCoordinates, testCategories),
        )

        placesDao.insertAllPlaces(testPlacesList)

        val placeResult = placesDao.getPlaceById(1)

        Assert.assertEquals("test_name1", placeResult.name)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun clearAllPlacesTest() = runTest {
        val testCoordinates = Main(0.0, 0.0)
        val testIcon = Icon("test_prefix", "test_suffix")
        val testCategories = listOf(Category(testIcon, "0", "test_icon_name"))
        val testPlacesList = listOf(
            Place(1, "test_name1", 5.0, "test_address", "test_timezone", testCoordinates, testCategories),
            Place(2, "test_name2", 5.0, "test_address", "test_timezone", testCoordinates, testCategories),
        )

        placesDao.insertAllPlaces(testPlacesList)
        placesDao.clearAllPlaces()

        val places = placesDao.getAllPlaces().first()

        Assert.assertEquals(emptyList<Place>(), places)
    }
}