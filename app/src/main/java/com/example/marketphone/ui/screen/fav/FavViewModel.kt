package com.example.marketphone.ui.screen.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketphone.data.MarketRepository
import com.example.marketphone.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavViewModel(
    private val repository: MarketRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavState>>
        get() = _uiState

    fun getAddedOrderMarkets() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderMarkets()
                .collect { orderMarket ->
                    val totalRequiredPoint =
                        orderMarket.sumOf { it.market.price * it.count }
                    _uiState.value = UiState.Success(FavState(orderMarket, totalRequiredPoint))
                }
        }
    }

    fun updateOrderReward(rewardId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderMarket(rewardId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderMarkets()
                    }
                }
        }
    }
}
