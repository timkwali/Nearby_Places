package com.adyen.android.assignment.common.data.cache.model

import com.adyen.android.assignment.common.data.api.model.*
import junit.framework.Assert.assertFalse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class PlaceItemMapperTest {
    private val location = Location("address", "country", "locality", listOf("neighbourhood"), "postcode", "region")
    private val result = Result(
        emptyList(),
        50,
        GeoCode(Main(0.0, 0.0)),
        location,
        "test_name1",
        "test_timezone"
    )

    @ExperimentalCoroutinesApi
    @Test
    fun `place item mapper returns valid place`() = runTest {
        val testCoordinates = Main(0.0, 0.0)
        val testPlace = Place(0, "test_name1", 50, "address postcode locality region", "test_timezone", testCoordinates, emptyList())

        val mappedPlace = PlaceItemMapper().mapToDomain(result)

        Assert.assertEquals(testPlace, mappedPlace)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `place item mapper does not return invalid user`() = runTest {
        val testCoordinates = Main(4.557888, 6.2545)
        val invalidTestPlace = Place(1, "test_name2", 500, "address", "timezone", testCoordinates, emptyList())

        val mappedPlace = PlaceItemMapper().mapToDomain(result)

        assertFalse(invalidTestPlace == mappedPlace)
    }
}