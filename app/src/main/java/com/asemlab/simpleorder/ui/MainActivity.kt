package com.asemlab.simpleorder.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.asemlab.simpleorder.ui.navigation.Destination
import com.asemlab.simpleorder.ui.navigation.MainBottomBar
import com.asemlab.simpleorder.ui.navigation.MainNavHost
import com.asemlab.simpleorder.ui.theme.SimpleOrderTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            SimpleOrderTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    /*.padding(top = 32.dp)*/
                    bottomBar = { MainBottomBar(navController) }
                ) { innerPadding ->

                    MainNavHost(navController, Destination.TABLES, innerPadding)

                }
            }
        }
    }
}

