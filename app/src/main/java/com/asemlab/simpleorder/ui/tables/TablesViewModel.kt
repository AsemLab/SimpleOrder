package com.asemlab.simpleorder.ui.tables

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asemlab.simpleorder.models.onError
import com.asemlab.simpleorder.models.onSuccess
import com.asemlab.simpleorder.repositories.ProductsRepository
import com.asemlab.simpleorder.ui.models.CartState
import com.asemlab.simpleorder.ui.models.CategoryTabItem
import com.asemlab.simpleorder.ui.models.ProductUI
import com.asemlab.simpleorder.ui.models.TablesState
import com.asemlab.simpleorder.ui.models.filterByNameAndCategory
import com.asemlab.simpleorder.ui.models.toCategory
import com.asemlab.simpleorder.ui.models.toCategoryTabItem
import com.asemlab.simpleorder.ui.models.toProductUI
import com.asemlab.simpleorder.ui.models.updateProductQuantity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TablesViewModel(private val repository: ProductsRepository) : ViewModel() {


    val state = MutableStateFlow(TablesState(cartState = CartState(items = emptyList())))
    private val products = MutableStateFlow(listOf<ProductUI>())
    private val categories = MutableStateFlow(listOf<CategoryTabItem>())


    init {
        getCategories()
    }

    fun getCategories() {
        launchCoroutine {
            repository.getCategories()
                .onSuccess { categoryList ->
                    categories.update { categoryList.map { it.toCategoryTabItem() } }
                    getProducts()

                }.onError { errorMessage ->
                    state.update {
                        it.copy(
                            categories = emptyList(),
                            errorMessage = errorMessage,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun getProducts() {
        launchCoroutine {
            repository.getProducts()
                .onSuccess { productList ->

                    products.update { productList.map { it.toProductUI() } }
                    filterProductsByCategory(categories.value.first())

                }.onError { errorMessage ->
                    products.update { emptyList() }

                    state.update {
                        it.copy(
                            products = emptyList(),
                            errorMessage = errorMessage,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun filterProductsByCategory(category: CategoryTabItem) {
        launchCoroutine {
            state.update { newState ->
                newState.copy(
                    categories = categories.value,
                    errorMessage = "",
                    products =
                        repository.getProductsByCategory(category.toCategory())
                            .map { it.toProductUI() },
                    selectedCategory = category
                )
            }
            searchProducts(state.value.searchQuery)
        }
    }

    fun searchProducts(query: String) {
        launchCoroutine {
            state.update { state ->
                val filtered =
                    products.value.filterByNameAndCategory(
                        query,
                        state.selectedCategory?.id
                    )

                state.copy(products = filtered, searchQuery = query, isLoading = false)
            }
        }
    }

    fun updateCart(productUI: ProductUI) {
        state.update { state ->

            // Increase the product quantity in local products list
            val updatedProducts =
                products.value.updateProductQuantity(productUI, productUI.count + 1).run {

                    products.update { this }

                    filterByNameAndCategory(
                        state.searchQuery,
                        state.selectedCategory?.id
                    ).toMutableList()

                }

            // Increase the product quantity in cartState
            val updatedCartState = updatedCartState(state, productUI)

            state.copy(cartState = updatedCartState, products = updatedProducts)

        }
    }

    private fun updatedCartState(
        tablesState: TablesState,
        productUI: ProductUI
    ): CartState {

        val updatedCartItems =
            tablesState.cartState.items.updateProductQuantity(productUI, productUI.count + 1)

        val itemCounts = updatedCartItems.sumOf { p ->
            p.count
        }

        val totalAmount = updatedCartItems.sumOf { p ->
            p.price?.times(p.count) ?: 0.0
        }

        // Create new CartState object to trigger the Flow to update the UI
        val updatedCartState = CartState(
            itemCounts,
            totalAmount,
            updatedCartItems
        )

        return updatedCartState
    }


    fun clearCart() {
        state.update { state ->

            var updatedProducts = products.value.toMutableList()

            updatedProducts.forEachIndexed { index, product ->
                updatedProducts[index] = product.copy(count = 0)
            }

            products.update { updatedProducts }

            updatedProducts = updatedProducts.filterByNameAndCategory(
                state.searchQuery,
                state.selectedCategory?.id
            ).toMutableList()

            state.copy(cartState = CartState(items = emptyList()), products = updatedProducts)
        }
    }

    private suspend fun isLoading(duration: Long = 500) {
        state.update {
            it.copy(isLoading = true)
        }
        delay(duration)
    }

    private fun launchCoroutine(block: suspend () -> Unit) {

        val handler = CoroutineExceptionHandler { _, throwable ->
            state.update {
                it.copy(
                    errorMessage = throwable.message ?: "Unknown error",
                    isLoading = false
                )
            }
        }

        viewModelScope.launch(handler) {
            withContext(Dispatchers.IO) {
                isLoading()
                block()
            }
        }
    }

}