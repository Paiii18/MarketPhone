package com.example.marketphone.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketphone.data.MarketRepository
import com.example.marketphone.model.Market
import com.example.marketphone.model.OrderMarket
import com.example.marketphone.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel (
    private val repository: MarketRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderMarket>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderMarket>>
        get() = _uiState

    fun getMarketById(marketId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderMarketById(marketId))
        }
    }

    fun addToCart(market: Market, count: Int) {
        viewModelScope.launch {
            repository.updateOrderMarket(market.id, count)
        }
    }
}