package com.asemlab.simpleorder.usecases.products

import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.repositories.ProductsRepository

class GetProductsByCategoryUseCase(
    private val productsRepository: ProductsRepository
) {

    suspend fun invoke(category: Category) = productsRepository.getProductsByCategory(category)

}