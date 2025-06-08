package com.asemlab.simpleorder.di


import androidx.room.Room
import com.asemlab.simpleorder.database.ProductDB
import com.asemlab.simpleorder.database.ProductDao
import org.koin.dsl.module


val databaseModule = module {

    single<ProductDB> {
        Room.databaseBuilder(
            get(),
            ProductDB::class.java,
            "products_db"
        ).build()
    }

    single<ProductDao> {
        get<ProductDB>().getProductDao()
    }

}