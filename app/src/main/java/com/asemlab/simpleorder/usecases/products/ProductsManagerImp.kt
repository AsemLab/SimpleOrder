package com.asemlab.simpleorder.usecases.products

import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.models.Product
import com.asemlab.simpleorder.models.ServerResponse

class ProductsManagerImp(
    private val getProductsUseCase: GetProductsUseCase,
    private val addProductsUseCase: AddProductsUseCase,
    private val clearProductsUseCase: ClearProductsUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
) : ProductsManager {

    override suspend fun getProducts(): ServerResponse<List<Product>> {
        return getProductsUseCase.invoke()
    }

    override suspend fun addProducts(list: List<Product>) {
        return addProductsUseCase.invoke(list)
    }

    override suspend fun clearProducts() {
        return clearProductsUseCase.invoke()
    }

    override suspend fun getProductsByCategory(category: Category): List<Product> {
        return getProductsByCategoryUseCase.invoke(category)
    }
}