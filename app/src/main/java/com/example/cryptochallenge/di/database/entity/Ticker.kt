package com.example.cryptochallenge.di.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptochallenge.domain.ticker.Payload

/**
 * DTO for Ticker record
 *
 * @property id Ticker id
 * @property book_name Book name
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
    tableName = "ticker_table"
)
data class Ticker(
    @PrimaryKey(autoGenerate = true) var id: Int,
    val book_name: String,
    val volume: Double?,
    val high: Double?,
    val last: Double?,
    val low: Double?,
    val vwap: Double?,
    val ask: Double?,
    val bid: Double?,
    val created_at: String?
) {
    companion object {
        /**
         * Transform ticker model to entity
         *
         * @param model Ticker model
         *
         * @return Ticker entity
         */
        fun toEntity(model: Payload): Ticker {
            return Ticker(
                0,
                model.book ?: "",
                model.volume,
                model.high,
                model.last,
                model.low,
                model.vwap,
                model.ask,
                model.bid,
                model.created_at
            )
        }

        /**
         * Transform entity to model
         *
         * @param entity Ticker entity
         *
         * @return Ticker model
         */
        fun toModel(entity: Ticker): Payload {
            return Payload(
                entity.book_name,
                entity.volume,
                entity.high,
                entity.last,
                entity.low,
                entity.vwap,
                entity.ask,
                entity.bid,
                entity.created_at
            )
        }
    }
}