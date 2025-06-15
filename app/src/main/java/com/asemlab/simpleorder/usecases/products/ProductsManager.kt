package com.asemlab.simpleorder.usecases.products

import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.models.Product
import com.asemlab.simpleorder.models.ServerResponse

interface ProductsManager {

    suspend fun getProducts(): ServerResponse<List<Product>>
    suspend fun addProducts(list: List<Product>)
    suspend fun clearProducts()
    suspend fun getProductsByCategory(category: Category): List<Product>

}