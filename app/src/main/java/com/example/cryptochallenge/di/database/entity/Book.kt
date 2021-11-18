package com.example.cryptochallenge.di.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptochallenge.domain.availablebook.Payload

/**
 * Database entity for available book objects
 *
 * @property id Record id
 * @property name Order book symbol
 * @property minimum_amount Minimum amount of major when placing orders
 * @property maximum_amount Maximum amount of major when placing orders
 * @property minimum_price Minimum price when placing orders
 * @property maximum_price Maximum price when placing orders
 * @property minimum_value Minimum value amount (amount*price) when placing orders
 * @property maximum_value Maximum value amount (amount*price) when placing orders
 */
@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "minimum_amount") var minimum_amount: Float?,
    @ColumnInfo(name = "maximum_amount") var maximum_amount: Float?,
    @ColumnInfo(name = "minimum_price") var minimum_price: Float?,
    @ColumnInfo(name = "maximum_price") var maximum_price: Float?,
    @ColumnInfo(name = "minimum_value") var minimum_value: Float?,
    @ColumnInfo(name = "maximum_value") var maximum_value: Float?
){
    companion object{

        /**
         * Transform entity to model
         *
         * @param book Book entity
         *
         * @return Available book model
         */
        fun toModel(book: Book):Payload{
            return Payload(
                book.name,
                book.minimum_amount,
                book.maximum_amount,
                book.minimum_price,
                book.maximum_price,
                book.minimum_value,
                book.maximum_value
            )
        }

        /**
         * Transform book model to entity
         *
         * @param model Book model
         *
         * @return Book entity
         */
        fun toEntity(model: Payload):Book{
            return Book(
                0,
                model.book,
                model.minimum_amount,
                model.maximum_amount,
                model.minimum_price,
                model.maximum_price,
                model.minimum_value,
                model.maximum_value
            )
        }
    }
}