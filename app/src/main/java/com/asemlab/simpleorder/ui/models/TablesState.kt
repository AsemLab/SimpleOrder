package com.asemlab.simpleorder.ui.models

data class TablesState(
    val categories: List<CategoryTabItem> = emptyList(),
    val products: List<ProductUI> = emptyList(),
    val errorMessage: String = "",
    var isLoading: Boolean = false,
    var selectedCategory: CategoryTabItem? = null,
    var searchQuery: String = ""
)