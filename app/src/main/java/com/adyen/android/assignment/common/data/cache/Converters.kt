package com.adyen.android.assignment.common.data.cache

import androidx.room.TypeConverter
import com.adyen.android.assignment.common.data.api.model.Category
import com.adyen.android.assignment.common.data.api.model.Icon
import com.adyen.android.assignment.common.data.api.model.Main
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    @TypeConverter
    fun toMain(value: String): Main {
        val listType: Type = object : TypeToken<Main>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromMain(main: Main): String {
        return Gson().toJson(main)
    }


    @TypeConverter
    fun toCategoryList(value: String): List<Category> {
        val listType: Type = object : TypeToken<List<Category>>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromCategoryList(categoryList: List<Category>): String {
        return Gson().toJson(categoryList)
    }


    @TypeConverter
    fun toIcon(value: String): Icon {
        val listType: Type = object : TypeToken<Icon>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromIcon(icon: Icon): String {
        return Gson().toJson(icon)
    }
}