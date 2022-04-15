package com.adyen.android.assignment.common.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adyen.android.assignment.common.data.cache.model.Place

@Dao
interface PlacesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlaces(places: List<Place>)

    @Query("SELECT * FROM places_table WHERE id LIKE :id")
    suspend fun getPlaceById(id: Int): Place

    @Query("DELETE FROM places_table")
    suspend fun clearAllPlaces()

}