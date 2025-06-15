package com.asemlab.simpleorder.usecases.categories

import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.models.ServerResponse

class CategoriesManagerImp(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addCategoriesUseCase: AddCategoriesUseCase,
    private val clearCategoriesUseCase: ClearCategoriesUseCase,
) : CategoriesManager {

    override suspend fun getCategories(): ServerResponse<List<Category>> {
        return getCategoriesUseCase.invoke()
    }

    override suspend fun addCategories(list: List<Category>) {
        return addCategoriesUseCase.invoke(list)
    }

    override suspend fun clearCategories() {
        return clearCategoriesUseCase.invoke()
    }

}