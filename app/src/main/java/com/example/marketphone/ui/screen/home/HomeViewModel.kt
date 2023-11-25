package com.example.marketphone.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketphone.data.MarketRepository
import com.example.marketphone.model.OrderMarket
import com.example.marketphone.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MarketRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderMarket>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderMarket>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query


    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
        if (newQuery.isBlank()) {
            getAllMarket()
        } else {
            searchMarket(newQuery)
        }
    }

    private fun getAllMarket() {
        viewModelScope.launch {
            repository.getAllMarkets()
                .catch { _uiState.value = UiState.Error(it.message.toString()) }
                .collect { _uiState.value = UiState.Success(it) }
        }
    }

    private fun searchMarket(query: String) {
        viewModelScope.launch {
            repository.searchHero(query)
                .catch { _uiState.value = UiState.Error(it.message.toString()) }
                .collect { _uiState.value = UiState.Success(it) }
        }
    }
}
