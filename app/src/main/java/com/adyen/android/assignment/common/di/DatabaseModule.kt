package com.adyen.android.assignment.common.di

import android.app.Application
import androidx.room.Room
//import com.adyen.android.assignment.common.data.cache.PlacesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object DatabaseModule {

//    @Provides
//    @Singleton
//    fun providePlacesDatabase(app: Application): PlacesDatabase {
//        return Room.databaseBuilder(
//            app,
//            PlacesDatabase::class.java,
//            PlacesDatabase.DATABASE_NAME
//        ).build()
//    }
//}