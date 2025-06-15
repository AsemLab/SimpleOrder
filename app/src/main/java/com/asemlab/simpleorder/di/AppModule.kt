package com.asemlab.simpleorder.di

import com.asemlab.simpleorder.database.ProductDao
import com.asemlab.simpleorder.remote.ProductsService
import com.asemlab.simpleorder.repositories.ProductsRepositoryImp
import com.asemlab.simpleorder.ui.tables.TablesViewModel
import com.asemlab.simpleorder.usecases.categories.CategoriesManager
import com.asemlab.simpleorder.usecases.products.ProductsManager
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<ProductsRepositoryImp> {
        ProductsRepositoryImp(get<ProductsService>(), get<ProductDao>())
    }

    viewModel { TablesViewModel(get<CategoriesManager>(), get<ProductsManager>()) }

}