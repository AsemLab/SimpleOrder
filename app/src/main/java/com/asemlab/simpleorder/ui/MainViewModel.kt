package com.asemlab.simpleorder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asemlab.simpleorder.models.onError
import com.asemlab.simpleorder.models.onSuccess
import com.asemlab.simpleorder.repositories.ProductsRepository
import com.asemlab.simpleorder.ui.models.CartState
import com.asemlab.simpleorder.ui.models.CategoryTabItem
import com.asemlab.simpleorder.ui.models.ProductUI
import com.asemlab.simpleorder.ui.models.TablesState
import com.asemlab.simpleorder.ui.models.toCategory
import com.asemlab.simpleorder.ui.models.toCategoryTabItem
import com.asemlab.simpleorder.ui.models.toProductUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: ProductsRepository) : ViewModel() {


    val state = MutableStateFlow(TablesState(cartState = CartState()))
    private val products = MutableStateFlow(listOf<ProductUI>())
    private val categories = MutableStateFlow(listOf<CategoryTabItem>())


    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                isLoading()

                repository.getCategories().onSuccess { categoryList ->
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
    }

    private fun getProducts() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                repository.getProducts().onSuccess { productList ->

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
    }


    fun filterProductsByCategory(category: CategoryTabItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                isLoading()
                state.update { state1 ->
                    state1.copy(
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
    }

    fun searchProducts(query: String) {
        state.update {
            val filtered = products.value.filter { product ->
                product.name?.contains(
                    query,
                    true
                ) == true && product.category?.id?.toInt() == it.selectedCategory?.id

            }
            it.copy(products = filtered, searchQuery = query, isLoading = false)
        }
    }

    fun updateCart(productUI: ProductUI) {
        state.update {

            var updatedProducts = products.value.toMutableList()

            updatedProducts.forEachIndexed { index, product ->
                if (product.id == productUI.id)
                    updatedProducts[index] = product.copy(count = product.count + 1)
            }

            products.update { updatedProducts }

            updatedProducts = updatedProducts.filter { product ->
                product.name?.contains(
                    it.searchQuery,
                    true
                ) == true && product.category?.id?.toInt() == it.selectedCategory?.id

            }.toMutableList()


            val updatedCartState = CartState(
                it.cartState.numOfItems + 1,
                (it.cartState.totalAmount + productUI.price!!)
            )

            it.copy(cartState = updatedCartState, products = updatedProducts)

        }
    }


    fun clearCart() {
        state.update {

            var updatedProducts = products.value.toMutableList()

            updatedProducts.forEachIndexed { index, product ->
                updatedProducts[index] = product.copy(count = 0)
            }

            products.update { updatedProducts }

            updatedProducts = updatedProducts.filter { product ->
                product.name?.contains(
                    it.searchQuery,
                    true
                ) == true && product.category?.id?.toInt() == it.selectedCategory?.id

            }.toMutableList()

            it.copy(cartState = CartState(), products = updatedProducts)
        }
    }

    private suspend fun isLoading() {
        state.update {
            it.copy(isLoading = true)
        }
        delay(1000)
    }

}