package com.asemlab.simpleorder.usecases.categories

import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.models.ServerResponse

interface CategoriesManager {

    suspend fun getCategories(): ServerResponse<List<Category>>
    suspend fun addCategories(list: List<Category>)
    suspend fun clearCategories()

}