package com.example.marketphone.ui.navigation

sealed class Screen (val route: String) {
    object Home : Screen("Home")
    object Fav : Screen("Wishlist")
    object Profile : Screen("Profile")
    object Detail : Screen("home/{rewardId}") {
        fun createRoute(marketId: Long) = "home/$marketId"
    }
}
