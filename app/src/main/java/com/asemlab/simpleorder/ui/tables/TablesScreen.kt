package com.asemlab.simpleorder.ui.tables


import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.asemlab.simpleorder.R
import com.asemlab.simpleorder.ui.base.LoadingIndicator
import com.asemlab.simpleorder.ui.models.CartState
import com.asemlab.simpleorder.ui.models.CategoryTabItem
import com.asemlab.simpleorder.ui.theme.OrderTitleBold
import com.asemlab.simpleorder.ui.theme.OrderTitleNormal
import com.asemlab.simpleorder.ui.theme.SimpleOrderTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun TablesScreen(
    tablesViewModel: TablesViewModel = koinViewModel(),
    modifier: Modifier,
) {
    val scrollState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    val state = tablesViewModel.state.collectAsStateWithLifecycle()

    SimpleOrderTheme {
        if (state.value.errorMessage.isNotEmpty()) {
            NoProducts(state.value.errorMessage, R.drawable.no_product, modifier)

        } else if (state.value.isLoading && state.value.categories.isEmpty()) {
            LoadingIndicator(modifier)
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize()
            ) {

                if (state.value.categories.isNotEmpty()) {
                    ProductsSearchBar(state.value.searchQuery) {
                        tablesViewModel.searchProducts(it)
                        coroutineScope.launch {
                            scrollState.animateScrollToItem(0)
                        }
                    }
                    CategoriesTab(
                        state.value.categories,
                        (state.value.selectedCategory?.id?.minus(1)) ?: 0
                    ) {
                        tablesViewModel.filterProductsByCategory(it)
                        coroutineScope.launch {
                            scrollState.scrollToItem(0)
                        }
                    }
                    if (state.value.isLoading) {
                        LoadingIndicator(modifier)
                    } else if (state.value.products.isNotEmpty()) {

                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            LazyVerticalGrid(
                                state = scrollState,
                                columns = GridCells.Adaptive(175.dp),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = if (state.value.cartState.numOfItems > 0) 64.dp else 0.dp)
                            ) {
                                items(state.value.products) { product ->
                                    ProductCardBox(product) {
                                        tablesViewModel.updateCart(product)
                                    }
                                }
                            }

                            if (state.value.cartState.numOfItems > 0) {
                                CartButton(state.value.cartState) {
                                    tablesViewModel.clearCart()
                                }
                            }
                        }
                    } else {
                        NoProducts(
                            stringResource(R.string.no_products),
                            R.drawable.no_product,
                            modifier
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun NoProducts(message: String, @DrawableRes imageId: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            ImageBitmap.imageResource(imageId),
            modifier = Modifier
                .height(250.dp),
            contentScale = ContentScale.FillHeight,
            contentDescription = "",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer)
        )
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ProductsSearchBar(
    query: String,
    onSearch: (String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf(query) }
    ProvideTextStyle(value = OrderTitleNormal) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = text,
                    onQueryChange = {
                        text = it
                        onSearch(text)
                    },
                    expanded = false,
                    onExpandedChange = {},
                    placeholder = {
                        Text(
                            stringResource(R.string.search_hint),
                            style = OrderTitleNormal
                        )
                    },
                    leadingIcon = {
                        IconButton(onClick = { onSearch(text) }) {
                            Icon(Icons.Default.Search, contentDescription = null)
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            text = ""
                            onSearch(text)
                        }) {
                            Icon(Icons.Default.Close, contentDescription = null)
                        }
                    },
                    onSearch = {
                        onSearch(text)
                    },
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .padding(horizontal = 4.dp).height(56.dp)
                )

            },

            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            shape = RoundedCornerShape(size = 8.dp),
            expanded = false,
            onExpandedChange = { }) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesTab(
    categories: List<CategoryTabItem>,
    selectedTab: Int,
    onSelectCategory: (CategoryTabItem) -> Unit
) {

    val scrollState = rememberScrollState()
    var selected by rememberSaveable { mutableIntStateOf(selectedTab) }

    PrimaryScrollableTabRow(
        scrollState = scrollState,
        selectedTabIndex = selected,
        modifier = Modifier.padding(horizontal = 16.dp), edgePadding = 0.dp
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = selected == index,
                onClick = {
                    selected = index

                    onSelectCategory(category)
                },
                text = {
                    Text(
                        text = category.label,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    }
}

@Composable
fun CartButton(cart: CartState, modifier: Modifier = Modifier, onClick: () -> Unit) {

    val format = String.format("JOD %.2f", cart.totalAmount)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 8.dp)
            .padding(bottom = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
    ) {
        Spacer(modifier = modifier.width(16.dp))
        Text(
            "${cart.numOfItems}",
            style = OrderTitleBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = CircleShape
                )
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 12.dp)
                .weight(.1f, true),
            color = MaterialTheme.colorScheme.onPrimaryContainer

        )
        TextStyle()
        Text(
            stringResource(R.string.view_order),
            style = OrderTitleBold,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier
                .padding(8.dp)
                .weight(.4f),
        )
        Text(
            format,
            style = OrderTitleBold,
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier
                .padding(8.dp)
                .weight(.3f)

        )
        Icon(
            Icons.AutoMirrored.Filled.ArrowForward,
            modifier = modifier
                .weight(.1f),
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = " "
        )
    }
}


@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "NoProducts Preview")
private fun NoProductsPreview() {
    SimpleOrderTheme {
        NoProducts("No products", R.drawable.no_product)
    }
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Cart button Preview")
private fun CartButtonPreview() {
    SimpleOrderTheme {
        CartButton(CartState(15, 125.33, emptyList())) {

        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Cart button Dark Preview")
private fun CartButtonDarkPreview() {
    SimpleOrderTheme {
        CartButton(CartState(15, 125.33, emptyList())) {
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Search bar Preview")
private fun SearchBarPreview() {
    SimpleOrderTheme {
        ProductsSearchBar("") {
        }
    }
}