package com.asemlab.simpleorder.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.asemlab.simpleorder.R
import com.asemlab.simpleorder.ui.theme.SimpleOrderTheme
import com.asemlab.simpleorder.ui.theme.Typography

@Composable
fun DefaultScreen(title: String, modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.default_screen, title),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = Typography.titleMedium
        )
    }
}

@Preview(name = "Default screen preview")
@Composable
fun DefaultScreenPreview(){
    SimpleOrderTheme {
        DefaultScreen("Home")
    }
}