package com.asemlab.simpleorder.ui.navigation


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.asemlab.simpleorder.ui.base.DefaultScreen
import com.asemlab.simpleorder.ui.tables.TablesScreen

@Composable
fun MainBottomBar(navController: NavHostController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val selected = backStackEntry.value?.destination?.route


    NavigationBar {
        Destination.entries.forEach { destination ->
            NavigationBarItem(
                selected = destination.route == selected,
                onClick = {
                    navController.navigate(destination.route) {

                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }

                },
                icon = {

                    Icon(
                        ImageVector.vectorResource(destination.icon),
                        contentDescription = destination.contentDescription
                    )
                },
                label = { Text(stringResource(destination.label)) }, alwaysShowLabel = false
            )
        }
    }
}


@Composable
fun MainNavHost(
    navController: NavHostController,
    startDestination: Destination,
    isLandscape: Boolean,
    innerPadding: PaddingValues
) {

    NavHost(
        navController = navController,
        startDestination.route
    ) {

        Destination.entries.forEach { entry ->
            composable(entry.route) {
                when (entry) {
                    Destination.TABLES -> {
                        TablesScreen(
                            modifier = Modifier.padding(
                                bottom = if (!isLandscape) innerPadding.calculateBottomPadding() else 0.dp,
                                start = if (isLandscape) 80.dp else 0.dp
                            )
                        )
                    }

                    Destination.ORDERS -> DefaultScreen(
                        title = stringResource(entry.label),
                        modifier = Modifier.padding(innerPadding)
                    )

                    Destination.MENU -> DefaultScreen(
                        title = stringResource(entry.label),
                        modifier = Modifier.padding(innerPadding)
                    )

                    Destination.SETTINGS -> DefaultScreen(
                        title = stringResource(entry.label),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}