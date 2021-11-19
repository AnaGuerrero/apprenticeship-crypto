package com.example.cryptochallenge.usecases

import com.example.cryptochallenge.data.repository.CryptoRepository
import com.example.cryptochallenge.domain.ticker.Payload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveLocalTicker(private val cryptoRepository: CryptoRepository) {
    suspend fun execute(ticker: Payload) = withContext(Dispatchers.IO) {
        cryptoRepository.saveTicker(ticker)
    }
}