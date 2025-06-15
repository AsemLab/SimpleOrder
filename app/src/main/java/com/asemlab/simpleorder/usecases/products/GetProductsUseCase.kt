package com.asemlab.simpleorder.usecases.products

import com.asemlab.simpleorder.repositories.ProductsRepository

class GetProductsUseCase(
    private val productsRepository: ProductsRepository
) {

    suspend fun invoke() = productsRepository.getProducts()

}