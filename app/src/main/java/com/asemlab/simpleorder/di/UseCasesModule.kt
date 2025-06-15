package com.asemlab.simpleorder.di

import com.asemlab.simpleorder.repositories.ProductsRepositoryImp
import com.asemlab.simpleorder.usecases.categories.AddCategoriesUseCase
import com.asemlab.simpleorder.usecases.categories.CategoriesManager
import com.asemlab.simpleorder.usecases.categories.CategoriesManagerImp
import com.asemlab.simpleorder.usecases.categories.ClearCategoriesUseCase
import com.asemlab.simpleorder.usecases.categories.GetCategoriesUseCase
import com.asemlab.simpleorder.usecases.products.AddProductsUseCase
import com.asemlab.simpleorder.usecases.products.ClearProductsUseCase
import com.asemlab.simpleorder.usecases.products.GetProductsByCategoryUseCase
import com.asemlab.simpleorder.usecases.products.GetProductsUseCase
import com.asemlab.simpleorder.usecases.products.ProductsManager
import com.asemlab.simpleorder.usecases.products.ProductsManagerImp
import org.koin.dsl.module


val productsUseCasesModule = module {

    factory {
        GetProductsUseCase(get<ProductsRepositoryImp>())
    }
    factory {
        AddProductsUseCase(get<ProductsRepositoryImp>())
    }
    factory {
        ClearProductsUseCase(get<ProductsRepositoryImp>())
    }
    factory {
        GetProductsByCategoryUseCase(get<ProductsRepositoryImp>())
    }

    single<ProductsManager> {
        ProductsManagerImp(get(), get(), get(), get())
    }


}

val categoriesUseCaseModule = module {

    factory {
        GetCategoriesUseCase(get<ProductsRepositoryImp>())
    }
    factory {
        AddCategoriesUseCase(get<ProductsRepositoryImp>())
    }
    factory {
        ClearCategoriesUseCase(get<ProductsRepositoryImp>())
    }

    single<CategoriesManager> {
        CategoriesManagerImp(get(), get(), get())
    }

}