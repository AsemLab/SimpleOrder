package com.asemlab.simpleorder.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.models.Product

@Database(entities = [Category::class, Product::class], exportSchema = false, version = 1)
@TypeConverters(value = [CategoryTypeConverter::class])
abstract class ProductDB : RoomDatabase() {

    abstract fun getProductDao(): ProductDao

}