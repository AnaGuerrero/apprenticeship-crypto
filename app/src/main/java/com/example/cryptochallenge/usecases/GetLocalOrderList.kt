package com.example.cryptochallenge.usecases

import com.example.cryptochallenge.data.repository.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetLocalOrderList(private val cryptoRepository: CryptoRepository) {
    suspend fun execute(bookName: String, type: String) = withContext(Dispatchers.IO) {
        return@withContext cryptoRepository.getLocalOrderListByBookName(bookName, type)
    }
}