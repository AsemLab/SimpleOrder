package com.asemlab.simpleorder.di


import com.asemlab.simpleorder.remote.ProductsService
import com.asemlab.simpleorder.remote.ProductsServiceImp
import com.asemlab.simpleorder.remote.Routes
import com.asemlab.simpleorder.repositories.ProductsRepository
import com.asemlab.simpleorder.ui.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.gson.gson
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.asemlab.simpleorder.BuildConfig

val networkModule = module {
    single<HttpClient> {
        HttpClient(Android) {
            install(ContentNegotiation) {
                gson {
                }
            }
            defaultRequest {
                url(Routes.BASE_URL)
                header("X-API-Key", BuildConfig.API_KEY)
            }
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.BODY
            }

        }
    }

    single<ProductsService> {
        ProductsServiceImp(get<HttpClient>())
    }

    single<ProductsRepository> {
        ProductsRepository(get<ProductsService>())
    }

    viewModel { MainViewModel(get<ProductsRepository>()) }

}