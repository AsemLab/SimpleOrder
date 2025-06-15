package com.asemlab.simpleorder.usecases.products

import com.asemlab.simpleorder.models.Product
import com.asemlab.simpleorder.repositories.ProductsRepository

class AddProductsUseCase(
    private val productsRepository: ProductsRepository
) {

    suspend fun invoke(products: List<Product>) = productsRepository.addProducts(products)

}