package com.asemlab.simpleorder.ui.base

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.asemlab.simpleorder.ui.theme.SimpleOrderTheme


@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}


@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Loading Preview")
private fun LoadingIndicatorPreview() {
    SimpleOrderTheme {
        LoadingIndicator()
    }
}