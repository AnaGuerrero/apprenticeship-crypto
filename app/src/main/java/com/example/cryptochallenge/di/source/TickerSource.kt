package com.example.cryptochallenge.di.source

import android.content.Context
import com.example.cryptochallenge.data.source.ITickerSource
import com.example.cryptochallenge.di.database.AppDatabase
import com.example.cryptochallenge.di.database.entity.Ticker
import com.example.cryptochallenge.domain.ticker.Payload

/**
 * Perform calls to ticker's table of app database
 *
 * @param context Application context
 */
class TickerSource(context: Context) : ITickerSource {

    /**
     * Property to handle ticker's table
     */
    private val tickerDAO = AppDatabase.getInstance(context).tickerDAO

    override fun insertTicker(ticker: Payload) {
        val oldTicker = tickerDAO.getTickerByBookName(ticker.book ?: "")
        if (oldTicker != null)
            tickerDAO.deleteTicker(oldTicker)
        tickerDAO.insertTicker(Ticker.toEntity(ticker))
    }

    override fun getTickerByBookName(bookName: String): Payload? {
        val entity = tickerDAO.getTickerByBookName(bookName)
        return if (entity != null)
            Ticker.toModel(entity)
        else
            null
    }
}