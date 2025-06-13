package com.asemlab.simpleorder.repositories

import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.models.Product
import com.asemlab.simpleorder.models.ServerResponse

interface ProductsRepository {

    suspend fun getCategories(): ServerResponse<List<Category>>
    suspend fun getProducts(): ServerResponse<List<Product>>
    suspend fun addCategories(list: List<Category>)
    suspend fun addProducts(list: List<Product>)
    suspend fun clearCategories()
    suspend fun clearProducts()
    suspend fun getProductsByCategory(category: Category): List<Product>
    
}