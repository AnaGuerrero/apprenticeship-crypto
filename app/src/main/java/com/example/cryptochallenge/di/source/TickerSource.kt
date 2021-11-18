package com.example.cryptochallenge.di.source

import android.content.Context
import com.example.cryptochallenge.data.source.ITickerSource
import com.example.cryptochallenge.di.database.AppDatabase

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
}