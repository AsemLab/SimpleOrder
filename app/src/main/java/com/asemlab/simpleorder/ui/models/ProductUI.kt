package com.asemlab.simpleorder.ui.models


import com.asemlab.simpleorder.models.Category
import com.asemlab.simpleorder.models.Product

data class ProductUI(
    val category: Category? = null,
    val description: String? = null,
    val id: String,
    val image: String? = null,
    val name: String? = null,
    val price: Double? = 0.0,
    var count: Int = 0
)

fun Product.toProductUI() = ProductUI(category, description, id, image, name, price, 0)

fun ProductUI.toProduct() = Product(category, description, id, image, name, price)