package com.asemlab.simpleorder.base


import android.app.Application
import com.asemlab.simpleorder.di.appModule
import com.asemlab.simpleorder.di.categoriesUseCaseModule
import com.asemlab.simpleorder.di.databaseModule
import com.asemlab.simpleorder.di.networkModule
import com.asemlab.simpleorder.di.productsUseCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SimpleOrderApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SimpleOrderApp)
            modules(
                appModule,
                networkModule,
                databaseModule,
                categoriesUseCaseModule,
                productsUseCasesModule
            )
        }
    }
}