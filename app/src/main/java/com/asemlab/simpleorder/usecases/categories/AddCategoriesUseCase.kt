package com.asemlab.simpleorder.usecases.categories

import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.repositories.ProductsRepository

class AddCategoriesUseCase(
    private val productsRepository: ProductsRepository
) {

    suspend fun invoke(categories: List<Category>) = productsRepository.addCategories(categories)

}