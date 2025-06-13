package com.asemlab.simpleorder.repositories


import com.asemlab.simpleorder.database.ProductDao
import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.models.Product
import com.asemlab.simpleorder.models.ServerResponse
import com.asemlab.simpleorder.models.onSuccess
import com.asemlab.simpleorder.remote.ProductsService


class ProductsRepositoryImp(
    private val productsService: ProductsService,
    private val productDao: ProductDao
):ProductsRepository {

    override suspend fun getCategories(): ServerResponse<List<Category>> {

        val categories = productDao.getAllCategories()

        return if (categories.isEmpty())
            productsService.getCategories().onSuccess {
                addCategories(it)
            }
        else
            ServerResponse.Success(categories)
    }

    override suspend fun getProducts(): ServerResponse<List<Product>> {

        val products = productDao.getAllProducts()

        return if (products.isEmpty())
            productsService.getProducts().onSuccess {
                addProducts(it)
            }
        else {
            ServerResponse.Success(products)
        }

    }


    override suspend fun addCategories(list: List<Category>) {
        productDao.addCategories(list)
    }

    override suspend fun addProducts(list: List<Product>) {
        productDao.addProducts(list)
    }

    override suspend fun clearCategories() {
        productDao.clearCategories()
    }

    override suspend fun clearProducts() {
        productDao.clearProducts()
    }

    override suspend fun getProductsByCategory(category: Category): List<Product> {
        return productDao.getProductsByCategory(category)
    }

}