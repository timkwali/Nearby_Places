package com.adyen.android.assignment.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adyen.android.assignment.common.data.api.model.Category
import com.adyen.android.assignment.common.data.api.model.Main
import com.adyen.android.assignment.common.utils.Constants

@Entity(tableName = Constants.PLACES_TABLE_NAME)
data class Place(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String?,
    val distance: Double?,
    val address: String?,
    val timeZone: String?,
    val coordinates: Main?,
    val categories: List<Category>?
)