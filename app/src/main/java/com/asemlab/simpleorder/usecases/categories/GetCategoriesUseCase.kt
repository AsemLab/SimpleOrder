package com.asemlab.simpleorder.usecases.categories

import com.asemlab.simpleorder.repositories.ProductsRepository

class GetCategoriesUseCase(
    private val productsRepository: ProductsRepository
) {

    suspend fun invoke() = productsRepository.getCategories()

}