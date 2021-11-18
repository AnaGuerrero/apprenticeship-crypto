package com.example.cryptochallenge.di.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * DTO for Ticker record
 *
 * @property id Ticker id
 * @property book_id Book id
 * @property volume Last 24 hours volume
 * @property high Last 24 hours price high
 * @property last Last traded price
 * @property low Last 24 hours price low
 * @property vwap Last 24 hours volume weighted average price: vwap
 * @property ask Lowest sell order
 * @property bid Highest buy order
 * @property created_at Timestamp at which the ticker was generated
 */
@Entity(
    tableName = "ticker_table",
    foreignKeys = [ForeignKey(
        entity = Book::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("book_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Ticker(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(index = true)
    val book_id: Long,
    val volume: Double?,
    val high: Double?,
    val last: Double?,
    val low: Double?,
    val vwap: Double?,
    val ask: Double?,
    val bid: Double?,
    val created_at: String?
)