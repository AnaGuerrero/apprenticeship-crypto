package com.example.cryptochallenge.usecases

import com.example.cryptochallenge.data.repository.CryptoRepository
import com.example.cryptochallenge.domain.orderbook.PayloadObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveLocalOrderList(private val cryptoRepository: CryptoRepository) {
    suspend fun execute(orderList: List<PayloadObject>, type: String) =
        withContext(Dispatchers.IO) {
            cryptoRepository.saveLocalOrderList(orderList, type)
        }
}