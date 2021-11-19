package com.example.cryptochallenge.di.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.cryptochallenge.domain.orderbook.PayloadObject

/**
 * Database entity for OrderBooks
 *
 * @property _id Orderbook id
 * @property book_name Book name
 * @property price Price per unit of major
 * @property amount Major amount in order
 * @property type Order book's type
 */
@Entity(
    tableName = "order_book_table",
    foreignKeys = [ForeignKey(
        entity = Book::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("book_name"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Order(
    @PrimaryKey(autoGenerate = true) var _id: Int,
    val book_name: String,
    val price: Double?,
    val amount: Double?,
    val type: String
) {
    companion object {

        /**
         * Transform order book model to entity
         *
         * @param model Order book model
         * @param type Order book's type
         *
         * @return Order book entity
         */
        fun toEntity(model: PayloadObject, type: String): Order {
            return Order(
                0,
                model.book ?: "",
                model.price,
                model.amount,
                type
            )
        }

        /**
         * Transform entity to model
         *
         * @param entity Order book entity
         *
         * @return Order book model
         */
        fun toModel(entity: Order): PayloadObject {
            return PayloadObject(
                entity.book_name,
                entity.price,
                entity.amount
            )
        }
    }
}