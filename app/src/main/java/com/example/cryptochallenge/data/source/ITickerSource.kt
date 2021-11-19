package com.example.cryptochallenge.data.source

import com.example.cryptochallenge.domain.ticker.Payload

/**
 * Handler local calls for the ticker's table
 */
interface ITickerSource {

    /**
     * Insert ticker in app database
     *
     * @param ticker Ticker info
     */
    fun insertTicker(ticker: Payload)

    /**
     * Get a ticker by its book name
     *
     * @param bookName Ticker's book name
     *
     * @return Ticker info
     */
    fun getTickerByBookName(bookName: String): Payload?
}