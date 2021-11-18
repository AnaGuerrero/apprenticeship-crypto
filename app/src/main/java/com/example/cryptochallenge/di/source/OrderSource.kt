package com.example.cryptochallenge.di.source

import android.content.Context
import com.example.cryptochallenge.data.source.IOrderSource
import com.example.cryptochallenge.di.database.AppDatabase

/**
 * Perform calls to orderbook's table
 *
 * @param context Application context
 */
class OrderSource(context: Context) : IOrderSource {

    /**
     * Property to handle order book's table
     */
    private val orderDAO = AppDatabase.getInstance(context).orderDAO
}