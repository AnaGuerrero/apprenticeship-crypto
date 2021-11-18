package com.example.cryptochallenge.data.repository

import android.content.Context
import com.example.cryptochallenge.data.source.IBookSource
import com.example.cryptochallenge.data.source.ICryptoRemoteSource
import com.example.cryptochallenge.data.source.IOrderSource
import com.example.cryptochallenge.data.source.ITickerSource
import com.example.cryptochallenge.di.source.BookSource
import com.example.cryptochallenge.di.source.CryptoRemoteSource
import com.example.cryptochallenge.di.source.OrderSource
import com.example.cryptochallenge.di.source.TickerSource
import com.example.cryptochallenge.domain.availablebook.Payload

/**
 * Repository for cryptocurrencies
 */
class CryptoRepository(context: Context) {
    /**
     * Property to handle requests
     */
    private val cryptoRemoteSource: ICryptoRemoteSource = CryptoRemoteSource()

    /**
     * Property to handle local book request
     */
    private val bookSource: IBookSource = BookSource(context)

    /**
     * Property to handle local order book request
     */
    private val orderSource: IOrderSource = OrderSource(context)

    /**
     * Propertu to handle local ticker requesst
     */
    private val tickerSource: ITickerSource = TickerSource(context)

    /**
     * Get all remote available books
     *
     * @return [LiveData] object with available books info or null if error
     */
    fun getRemoteAvailableBooks() = cryptoRemoteSource.getAvailableBooks()

    /**
     * Save locally available books
     *
     * @param bookList List of available books
     */
    fun saveLocalAvailableBooks(bookList: List<Payload>) = bookSource.insertBookList(bookList)

    /**
     * Get all local available books
     *
     * @return [LiveData] object with available books info or null if error
     */
    fun getLocalAvailableBooks() = bookSource.getAllBooks()

    /**
     * Get trading information of a specific book
     *
     * @param bookName Name of the book
     * @return [LiveData] object with ticker information or null if error
     */
    fun getTicker(bookName: String) = cryptoRemoteSource.getTicker(bookName)

    /**
     * Get all open books orders of a specific book
     *
     * @param bookName Name of the book
     * @return [LiveData] object with order book info or null if error
     */
    fun getOrderBook(bookName: String) = cryptoRemoteSource.getOrderBook(bookName)

    /**
     * Clean the disposable property
     */
    fun clear() {
        cryptoRemoteSource.clear()
        bookSource.clear()
    }
}