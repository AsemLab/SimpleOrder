package com.asemlab.simpleorder.remote


import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.models.Product
import com.asemlab.simpleorder.models.ServerResponse


interface ProductsService {

    suspend fun getCategories(): ServerResponse<List<Category>>

    suspend fun getProducts(): ServerResponse<List<Product>>

}