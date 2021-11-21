package com.example.cryptochallenge.di.source

import android.content.Context
import com.example.cryptochallenge.data.source.IOrderSource
import com.example.cryptochallenge.di.database.AppDatabase
import com.example.cryptochallenge.di.database.entity.Order
import com.example.cryptochallenge.domain.orderbook.PayloadObject

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

    override fun insertOrder(order: PayloadObject, type: String) {
        val id = orderDAO.insertOrder(Order.toEntity(order, type))
        val ilndilsnd = "ildknslakdnsald"
    }

    override fun insertOrderList(orderList: List<PayloadObject>, type: String) {
        val bookName = if (orderList.isNotEmpty()) orderList.first().book ?: "" else ""
        val oldOrderList = orderDAO.getListOrdersByBookName(bookName, type)
        oldOrderList?.forEach {
            deleteOrder(it)
        }
        orderList.forEach {
            insertOrder(it, type)
        }
    }

    /**
     * Delete an order book
     *
     * @param order Order book to delete
     */
    private fun deleteOrder(order: Order) {
        orderDAO.deleteOrder(order)
    }

    override fun getOrderListByBookName(
        bookName: String,
        type: String
    ): List<PayloadObject>? {
        val orderList = orderDAO.getListOrdersByBookName(bookName, type)
        return toModelList(orderList)
    }

    /**
     * Tranform an entity list to a model list
     *
     * @param entityList Entities list
     * @return Models list
     */
    private fun toModelList(entityList: List<Order>?): List<PayloadObject> {
        val modelList = mutableListOf<PayloadObject>()

        entityList?.forEach {
            modelList.add(Order.toModel(it))
        }

        return modelList
    }
}