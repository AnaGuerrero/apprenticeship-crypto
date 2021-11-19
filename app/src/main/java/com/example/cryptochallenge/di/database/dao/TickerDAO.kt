package com.example.cryptochallenge.di.database.dao

import androidx.room.*
import com.example.cryptochallenge.di.database.entity.Ticker

/**
 * Ticker's table data access object
 */
@Dao
interface TickerDAO {

    /**
     * Insert ticker in Ticker's table
     *
     * @param ticker Ticker to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTicker(ticker: Ticker): Long

    /**
     * Delete ticker in Ticker's table
     *
     * @param ticker Ticker to delete
     */
    @Delete
    fun deleteTicker(ticker: Ticker)

    /**
     * Get a ticker by its book name
     *
     * @param bookName Ticker's book name
     *
     * @return Ticker info
     */
    @Query("select * from ticker_table where book_name == :bookName")
    fun getTickerByBookName(bookName: String): Ticker?
}