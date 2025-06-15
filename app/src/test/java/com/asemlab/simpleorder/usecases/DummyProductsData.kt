package com.asemlab.simpleorder.usecases

import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.models.Product


object DummyProductsData {

    val dummyCategories = listOf(
        Category(id = "cat1", name = "Electronics"),
        Category(id = "cat2", name = "Books"),
        Category(id = "cat3", name = "Clothing"),
        Category(id = "cat4", name = "Home & Kitchen")
    )

    val dummyProducts = listOf(
        Product(
            id = "prod1",
            name = "Smartphone",
            description = "Latest Android smartphone with AMOLED display",
            image = "",
            price = 699.99,
            category = dummyCategories[0]
        ),
        Product(
            id = "prod2",
            name = "Wireless Headphones",
            description = "Noise-cancelling over-ear headphones",
            image = "",
            price = 149.99,
            category = dummyCategories[0]
        ),
        Product(
            id = "prod3",
            name = "Novel - The Great Gatsby",
            description = "A classic novel by F. Scott Fitzgerald",
            image = "",
            price = 12.99,
            category = dummyCategories[1]
        ),
        Product(
            id = "prod4",
            name = "Men's Jacket",
            description = "Water-resistant winter jacket",
            image = "",
            price = 89.99,
            category = dummyCategories[2]
        )
    )

    fun filterByCategory(category: Category): List<Product> {
        return dummyProducts.filter { p ->
            p.category == category
        }
    }
}

