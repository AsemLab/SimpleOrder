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

fun List<ProductUI>.filterByNameAndCategory(
    name: String,
    categoryId: Int?
): List<ProductUI> {
    return filter { p ->
        p.name?.contains(name, true) == true && p.category?.id?.toInt() == categoryId
    }
}

fun List<ProductUI>.updateProductQuantity(
    productUI: ProductUI,
    newCount: Int
): MutableList<ProductUI> {

    val updatedCartItems = this.toMutableList()

    // Search for the item
    val index = updatedCartItems.indexOfFirst { p ->
        p.id == productUI.id
    }

    // Add/Update the item
    if (index == -1) {
        updatedCartItems.add(productUI.copy(count = newCount))
    } else {
        updatedCartItems[index] = productUI.copy(count = newCount)
    }

    return updatedCartItems
}