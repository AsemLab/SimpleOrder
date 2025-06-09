package com.asemlab.simpleorder.ui.tables


import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PrimaryScrollableTabRow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.asemlab.simpleorder.R
import com.asemlab.simpleorder.ui.MainViewModel
import com.asemlab.simpleorder.ui.models.CategoryTabItem
import com.asemlab.simpleorder.ui.models.ProductUI
import com.asemlab.simpleorder.ui.theme.SimpleOrderTheme
import kotlinx.coroutines.launch


@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun TablesScreen(
    mainViewModel: MainViewModel,
    modifier: Modifier,
    onSelectCategory: (CategoryTabItem) -> Unit,
    onSearch: (String) -> Unit,
    onProductClicked: (ProductUI) -> Unit
) {
    val scrollState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    val state = mainViewModel.state.collectAsStateWithLifecycle()

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
                        onSearch(it)
                        coroutineScope.launch {
                            scrollState.animateScrollToItem(0)
                        }
                    }
                    CategoriesTab(
                        state.value.categories,
                        (state.value.selectedCategory?.id?.minus(1)) ?: 0
                    ) {
                        onSelectCategory(it)
                        coroutineScope.launch {
                            scrollState.animateScrollToItem(0)
                        }
                    }
                    if (state.value.isLoading) {
                        LoadingIndicator(modifier)
                    } else if (state.value.products.isNotEmpty()) {
                        LazyVerticalGrid(
                            state = scrollState,
                            columns = GridCells.Adaptive(175.dp)
                        ) {
                            items(state.value.products) { p ->
                                ProductCardBox(p) {
                                    onProductClicked(p)
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
private fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun NoProducts(message: String, @DrawableRes imageId: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(), Arrangement.Center) {
        Image(
            ImageBitmap.imageResource(imageId),
            modifier = Modifier
                .height(250.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.FillHeight,
            contentDescription = ""
        )
        Text(message, modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ProductsSearchBar(
    query: String,
    onSearch: (String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf(query) }

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
                placeholder = { Text(stringResource(R.string.search_hint)) },
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
                        color = Color.LightGray,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .padding(horizontal = 8.dp),
            )

        },
        modifier = Modifier.padding(vertical = 8.dp),
        shape = RoundedCornerShape(size = 8.dp),
        expanded = false,
        onExpandedChange = { }) {

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
    val context = LocalContext.current

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
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "NoProducts Preview")
private fun NoProductsPreview() {
    SimpleOrderTheme {
        NoProducts("No products", R.drawable.no_product)
    }
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Loading Preview")
private fun LoadingIndicatorPreview() {
    SimpleOrderTheme {
        LoadingIndicator()
    }
}
