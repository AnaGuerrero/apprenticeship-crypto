package com.example.cryptochallenge.usecases

import com.example.cryptochallenge.data.repository.CryptoRepository
import com.example.cryptochallenge.domain.availablebook.Payload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Use case that save available book's list locally
 *
 * @property cryptoRepository Instance of [CryptoRepository]
 */
class SaveLocalAvailableBooks(private val cryptoRepository: CryptoRepository) {

    /**
     * Execute use case that save available book's list locally
     *
     * @param bookList Book's list
     */
    suspend fun execute(bookList: List<Payload>) = withContext(Dispatchers.IO) {
        cryptoRepository.saveLocalAvailableBooks(bookList)
    }
}