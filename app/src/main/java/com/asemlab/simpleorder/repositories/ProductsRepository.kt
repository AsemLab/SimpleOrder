package com.asemlab.simpleorder.repositories


import com.asemlab.simpleorder.database.ProductDao
import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.models.Product
import com.asemlab.simpleorder.models.ServerResponse
import com.asemlab.simpleorder.models.onSuccess
import com.asemlab.simpleorder.remote.ProductsService


class ProductsRepository(
    private val productsService: ProductsService,
    private val productDao: ProductDao
) {

    suspend fun getCategories(): ServerResponse<List<Category>> {

        val categories = productDao.getAllCategories()

        return if (categories.isEmpty())
            productsService.getCategories().onSuccess {
                addCategories(it)
            }
        else
            ServerResponse.Success(categories)
    }

    suspend fun getProducts(): ServerResponse<List<Product>> {

        val products = productDao.getAllProducts()

        return if (products.isEmpty())
            productsService.getProducts().onSuccess {
                addProducts(it)
            }
        else {
            ServerResponse.Success(products)
        }

    }


    suspend fun addCategories(list: List<Category>) {
        productDao.addCategories(list)
    }

    suspend fun addProducts(list: List<Product>) {
        productDao.addProducts(list)
    }

    suspend fun clearCategories() {
        productDao.clearCategories()
    }

    suspend fun clearProducts() {
        productDao.clearProducts()
    }

    suspend fun getProductsByCategory(category: Category): List<Product> {
        return productDao.getProductsByCategory(category)
    }

}