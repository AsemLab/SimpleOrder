package com.asemlab.simpleorder.remote


import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.models.Product
import com.asemlab.simpleorder.models.ServerResponse
import com.asemlab.simpleorder.utils.performRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get


class ProductsServiceImp(private val client: HttpClient) : ProductsService {

    override suspend fun getCategories(): ServerResponse<List<Category>> {
        return performRequest<List<Category>> {
            client.get(Routes.CATEGORIES_ENDPOINT)
        }
    }

    override suspend fun getProducts(): ServerResponse<List<Product>> {
        return performRequest<List<Product>> {
            client.get(Routes.PRODUCTS_ENDPOINT)
        }
    }
}