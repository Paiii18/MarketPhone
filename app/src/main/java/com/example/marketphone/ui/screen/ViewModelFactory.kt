package com.example.marketphone.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marketphone.data.MarketRepository
import com.example.marketphone.ui.screen.detail.DetailViewModel
import com.example.marketphone.ui.screen.fav.FavViewModel
import com.example.marketphone.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: MarketRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}