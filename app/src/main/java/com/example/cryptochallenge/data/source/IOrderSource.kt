package com.example.cryptochallenge.data.source

import com.example.cryptochallenge.domain.orderbook.PayloadObject

/**
 * Handler local calls for the order book's table
 */
interface IOrderSource {

    /**
     * Insert an order book
     *
     * @param order Order book
     * @param type Order book's type
     */
    fun insertOrder(order: PayloadObject, type: String)

    /**
     * Insert an order book's list
     *
     * @param orderList Order book's list
     * @param type Order book's type
     */
    fun insertOrderList(orderList: List<PayloadObject>, type: String)

    /**
     * Get a order book's list by its book name
     *
     * @param bookName Order book's list book name
     * @param type Order book's type
     */
    fun getOrderListByBookName(bookName: String, type: String): List<PayloadObject>?
}