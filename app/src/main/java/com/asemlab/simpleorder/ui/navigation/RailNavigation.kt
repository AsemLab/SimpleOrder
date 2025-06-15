package com.asemlab.simpleorder.ui.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun AppNavRail(navController: NavHostController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val selected = backStackEntry.value?.destination?.route

    NavigationRail(
        header = {
            Icon(
                ImageVector.vectorResource(Destination.TABLES.icon),
                null,
                Modifier.padding(vertical = 12.dp)
            )
        },
    ) {
        Spacer(Modifier.weight(1f))
        Destination.entries.forEach { destination ->
            NavigationRailItem(
                selected = selected == destination.route,
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
                        stringResource(destination.label)
                    )
                },
                label = { Text(stringResource(destination.label)) },
                alwaysShowLabel = false
            )
        }
        Spacer(Modifier.weight(1f))
    }
}