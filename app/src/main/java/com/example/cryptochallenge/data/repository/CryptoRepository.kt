package com.example.cryptochallenge.data.repository

import android.content.Context
import com.example.cryptochallenge.data.services.CryptoDetailServices
import com.example.cryptochallenge.data.source.IBookSource
import com.example.cryptochallenge.data.source.IOrderSource
import com.example.cryptochallenge.data.source.ITickerSource
import com.example.cryptochallenge.di.source.BookSource
import com.example.cryptochallenge.di.source.CryptoRemoteSource
import com.example.cryptochallenge.di.source.OrderSource
import com.example.cryptochallenge.di.source.TickerSource
import com.example.cryptochallenge.domain.availablebook.Payload
import com.example.cryptochallenge.domain.orderbook.PayloadObject
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for cryptocurrencies
 */
@Singleton
class CryptoRepository @Inject constructor(
    cryptoDetailServices: CryptoDetailServices,
    @ApplicationContext private val context: Context
) {
    private val cryptoRemoteSource = CryptoRemoteSource(cryptoDetailServices)

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
     * @return Object with available books info or null if error
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
     * @return Object with available books info or null if error
     */
    fun getLocalAvailableBooks() = bookSource.getAllBooks()

    /**
     * Get trading information of a specific book
     *
     * @param bookName Name of the book
     * @return Object with ticker information or null if error
     */
    fun getTicker(bookName: String) = cryptoRemoteSource.getTicker(bookName)

    /**
     * Get all open books orders of a specific book
     *
     * @param bookName Name of the book
     * @return Object with order book info or null if error
     */
    fun getOrderBook(bookName: String) = cryptoRemoteSource.getOrderBook(bookName)

    /**
     * Save locally ticker information
     *
     * @param ticker Ticker information
     */
    fun saveTicker(ticker: com.example.cryptochallenge.domain.ticker.Payload) =
        tickerSource.insertTicker(ticker)

    /**
     * Get local ticker information by its bookName
     *
     * @param bookName Ticker bookname
     * @return Object with ticker information or null if error
     */
    fun getLocalTickerByBookName(bookName: String) = tickerSource.getTickerByBookName(bookName)

    /**
     * Save local order book's list
     *
     * @param orderList Order book's list
     */
    fun saveLocalOrderList(orderList: List<PayloadObject>, type: String) =
        orderSource.insertOrderList(orderList, type)

    /**
     * Get local order book's list by its book name
     *
     * @param bookName Book name
     * @return Order book's list
     */
    fun getLocalOrderListByBookName(bookName: String, type: String) =
        orderSource.getOrderListByBookName(bookName, type)

    /**
     * Clean the disposable property
     */
    fun clear() {
        cryptoRemoteSource.clear()
        bookSource.clear()
    }
}