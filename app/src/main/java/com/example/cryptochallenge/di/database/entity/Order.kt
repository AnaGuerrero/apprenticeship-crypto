package com.example.cryptochallenge.di.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Database entity for OrderBooks
 *
 * @property _id Orderbook id
 * @property book_id Book id
 * @property price Price per unit of major
 * @property amount Major amount in order
 * @property updated_at Timestamp at which the order was last updated
 * @property sequence Increasing integer value for each order book update.
 */
@Entity(
    tableName = "order_book_table",
    foreignKeys = [ForeignKey(
        entity = Book::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("book_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Order(
    @PrimaryKey(autoGenerate = true) var _id: Int,
    @ColumnInfo(index = true)
    val book_id: Long,
    val price: Double?,
    val amount: Double?,
    val updated_at: String?,
    val sequence: Int?
)