package com.asemlab.simpleorder.ui.models

import com.asemlab.simpleorder.models.Category

data class CategoryTabItem(
    val id: Int,
    val label: String
)

fun Category.toCategoryTabItem() = CategoryTabItem(id.toInt(), name ?: "")

fun CategoryTabItem.toCategory() = Category(id.toString(), label)