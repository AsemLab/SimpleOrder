package com.asemlab.simpleorder.ui.models

data class CartState(
    var numOfItems: Int = 0,
    var totalAmount: Double = 0.0,
    val items: List<ProductUI>
)
