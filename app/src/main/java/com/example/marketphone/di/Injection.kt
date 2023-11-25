package com.example.marketphone.di

import com.example.marketphone.data.MarketRepository

object Injection {
    fun provideRepository(): MarketRepository {
        return MarketRepository.getInstance()
    }
}