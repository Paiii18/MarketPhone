package com.example.marketphone.ui.screen.fav

import com.example.marketphone.model.OrderMarket

data class FavState(
    val orderMarket: List<OrderMarket>,
    val totalRequiredPoint: Int
)
