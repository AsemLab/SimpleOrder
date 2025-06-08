package com.asemlab.simpleorder.repositories


import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.models.Product
import com.asemlab.simpleorder.models.ServerResponse
import com.asemlab.simpleorder.remote.ProductsService


class ProductsRepository(private val productsService: ProductsService) {

    suspend fun getCategories(): ServerResponse<List<Category>> {
        return productsService.getCategories()
    }

    suspend fun getProducts(): ServerResponse<List<Product>> {
        return productsService.getProducts()
    }

}