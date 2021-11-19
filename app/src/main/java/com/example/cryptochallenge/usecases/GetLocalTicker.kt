package com.example.cryptochallenge.usecases

import com.example.cryptochallenge.data.repository.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetLocalTicker(private val cryptoRepository: CryptoRepository) {
    suspend fun execute(bookName: String) = withContext(Dispatchers.IO) {
        cryptoRepository.getLocalTickerByBookName(bookName)
    }
}