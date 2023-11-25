package com.example.marketphone.ui.navigation

sealed class Screen (val route: String) {
    object Home : Screen("Home")
    object Fav : Screen("Wishlist")
    object Profile : Screen("Profile")
}
