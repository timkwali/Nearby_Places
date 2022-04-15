package com.adyen.android.assignment.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adyen.android.assignment.common.data.cache.dao.PlacesDao
import com.adyen.android.assignment.common.data.cache.model.Place
import com.adyen.android.assignment.common.utils.Constants

@Database(
    entities = [Place::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PlacesDatabase: RoomDatabase() {

    abstract fun placesDao(): PlacesDao

    companion object {
        const val DATABASE_NAME = Constants.PLACES_DATABASE_NAME
    }
}