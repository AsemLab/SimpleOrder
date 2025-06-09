package com.asemlab.simpleorder.ui.navigation

import androidx.annotation.DrawableRes
import com.asemlab.simpleorder.R

enum class Destination(
    val label: String,
    val route: String,
    @DrawableRes val icon: Int,
    val contentDescription: String
) {
    TABLES(
        "Tables",
        "tables",
        R.drawable.ic_tables,
        "Tables bottom item"
    ),
    ORDERS(
        "Orders",
        "orders",
        R.drawable.ic_orders,
        "Orders bottom item"
    ),
    MENU(
        "Menu",
        "menu",
        R.drawable.ic_menu,
        "Menu bottom item"
    ),
    SETTINGS(
        "Settings",
        "settings",
        R.drawable.ic_settings,
        "Settings bottom item"
    )
}