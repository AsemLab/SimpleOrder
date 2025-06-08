package com.asemlab.simpleorder.database


import androidx.room.TypeConverter
import com.asemlab.simpleorder.models.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CategoryTypeConverter {

    @TypeConverter
    fun fromCategory(value: Category): String? {
        val gson = Gson()
        val type = object : TypeToken<Category>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCategory(value: String): Category {
        val gson = Gson()
        val type = object : TypeToken<Category>() {}.type
        return gson.fromJson(value, type)
    }
}