package com.asemlab.simpleorder.usecases.categories

import com.asemlab.simpleorder.repositories.ProductsRepository

class ClearCategoriesUseCase(
    private val productsRepository: ProductsRepository
) {

    suspend fun invoke() = productsRepository.clearCategories()

}