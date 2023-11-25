package com.example.marketphone.data

import com.example.marketphone.model.FakeMarketDataSource
import com.example.marketphone.model.OrderMarket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MarketRepository {

    private val orderMarkets = mutableListOf<OrderMarket>()

    init {
        if (orderMarkets.isEmpty()) {
            FakeMarketDataSource.dummyMarket.forEach {
                orderMarkets.add(OrderMarket(it, 0))
            }
        }
    }

    fun getAllMarkets(): Flow<List<OrderMarket>> {
        return flowOf(orderMarkets)
    }

    fun getOrderMarketById(marketId: Long): OrderMarket {
        return orderMarkets.first {
            it.market.id == marketId
        }
    }

    fun updateOrderMarket(marketId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderMarkets.indexOfFirst { it.market.id == marketId }
        val result = if (index >= 0) {
            val orderMarket = orderMarkets [index]
            orderMarkets[index] =
                orderMarket.copy(market = orderMarket.market, count =  newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun searchHero(query: String): Flow<List<OrderMarket>> {
        return flow {
            emit(orderMarkets.filter {
                it.market.title.contains(query, ignoreCase = true)
            })
        }
    }

    fun getAddedOrderMarkets(): Flow<List<OrderMarket>> {
        return getAllMarkets()
            .map {orderMarkets ->
                orderMarkets.filter { orderMarkets ->
                    orderMarkets.count != 0
                }

            }
    }

    companion object {
        @Volatile
        private var instance: MarketRepository? = null

        fun getInstance(): MarketRepository =
            instance ?: synchronized(this) {
                MarketRepository().apply {
                    instance = this
                }
            }
    }
}