package com.asemlab.simpleorder.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.models.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCategories(list: List<Category>)

    @Query("SELECT * FROM Category")
    fun getAllCategories(): List<Category>

    @Query("DELETE FROM Category")
    fun clearCategories()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addProducts(list: List<Product>)

    @Query("SELECT * FROM Product")
    fun getAllProducts(): List<Product>

    @Query("DELETE FROM Product")
    fun clearProducts()

    @Query("SELECT * FROM Product WHERE category = :category")
    fun getProductsByCategory(category: Category): List<Product>

}