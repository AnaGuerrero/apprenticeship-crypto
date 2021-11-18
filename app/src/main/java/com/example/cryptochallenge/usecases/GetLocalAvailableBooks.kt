package com.example.cryptochallenge.usecases

import com.example.cryptochallenge.data.repository.CryptoRepository

/**
 * Use case that get local available book's list
 *
 * @property cryptoRepository Instance of [CryptoRepository]
 */
class GetLocalAvailableBooks(private val cryptoRepository: CryptoRepository) {

    /**
     * Execute use case that get local available book's list
     *
     * @return List of available book's list
     */
    fun execute() = cryptoRepository.getLocalAvailableBooks()
}