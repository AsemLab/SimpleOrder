package com.asemlab.simpleorder.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.asemlab.simpleorder.R

enum class Destination(
    @StringRes val label: Int,
    val route: String,
    @DrawableRes val icon: Int,
    val contentDescription: String
) {
    TABLES(
        R.string.tables_title,
        "tables",
        R.drawable.ic_tables,
        "Tables bottom item"
    ),
    ORDERS(
        R.string.orders_title,
        "orders",
        R.drawable.ic_orders,
        "Orders bottom item"
    ),
    MENU(
        R.string.menu_title,
        "menu",
        R.drawable.ic_menu,
        "Menu bottom item"
    ),
    SETTINGS(
        R.string.settings_title,
        "settings",
        R.drawable.ic_settings,
        "Settings bottom item"
    )
}