package com.example.cryptochallenge.di.database.dao

import androidx.room.*
import com.example.cryptochallenge.di.database.entity.Order

/**
 * Order book's table data access object
 */
@Dao
interface OrderDAO {

    /**
     * Insert order book in Order Book's table
     *
     * @param orderBook Order book to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(orderBook: Order)

    /**
     * Delete order book in Order Book's table
     *
     * @param orderBook Order book to delete
     */
    @Delete
    fun deleteOrder(orderBook: Order)

    /**
     * Get a order book's list by its book name
     *
     * @param bookName Order book's list book name
     * @param type Order book's type
     */
    @Query("select * from order_book_table where book_name == :bookName AND type == :type")
    fun getListOrdersByBookName(bookName: String, type: String): List<Order>?
}