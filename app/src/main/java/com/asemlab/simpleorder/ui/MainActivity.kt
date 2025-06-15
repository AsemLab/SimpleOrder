package com.asemlab.simpleorder.ui

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import androidx.window.layout.WindowMetricsCalculator
import com.asemlab.simpleorder.ui.navigation.AppNavRail
import com.asemlab.simpleorder.ui.navigation.Destination
import com.asemlab.simpleorder.ui.navigation.MainBottomBar
import com.asemlab.simpleorder.ui.navigation.MainNavHost
import com.asemlab.simpleorder.ui.theme.SimpleOrderTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Support landscape only on large screen
        requestedOrientation = if (compactScreen())
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT else
            ActivityInfo.SCREEN_ORIENTATION_FULL_USER

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentOrientation = LocalConfiguration.current.orientation
            val isLandscape = currentOrientation == Configuration.ORIENTATION_LANDSCAPE

            SimpleOrderTheme {
                Surface {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        /*.padding(top = 32.dp)*/
                        bottomBar = {
                            if (!isLandscape) {
                                MainBottomBar(navController)
                            }
                        }
                    ) { innerPadding ->

                        MainNavHost(navController, Destination.TABLES, isLandscape, innerPadding)
                        if (isLandscape) {
                            AppNavRail(navController)
                        }

                    }
                }
            }
        }
    }

    fun compactScreen(): Boolean {
        val metrics = WindowMetricsCalculator.getOrCreate().computeMaximumWindowMetrics(this)
        val width = metrics.bounds.width()
        val height = metrics.bounds.height()
        val density = resources.displayMetrics.density
        val windowSizeClass = WindowSizeClass.compute(width / density, height / density)

        return windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT ||
                windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.COMPACT
    }

}
