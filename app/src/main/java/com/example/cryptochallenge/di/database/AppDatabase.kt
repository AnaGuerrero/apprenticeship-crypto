package com.example.cryptochallenge.di.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptochallenge.di.database.dao.BookDAO
import com.example.cryptochallenge.di.database.dao.OrderDAO
import com.example.cryptochallenge.di.database.dao.TickerDAO
import com.example.cryptochallenge.di.database.entity.Book
import com.example.cryptochallenge.di.database.entity.Order
import com.example.cryptochallenge.di.database.entity.Ticker

/**
 * Application database
 */
@Database(
    entities = [Book::class, Order::class, Ticker::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Book's table data access object
     */
    abstract val bookDAO: BookDAO

    /**
     * Order book's table data access object
     */
    abstract val orderDAO: OrderDAO

    /**
     * Ticker's table data access object
     */
    abstract val tickerDAO: TickerDAO

    companion object {
        /**
         * Database instance
         */
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Get database instance
         *
         * @param context Application context
         * @return Database instance
         */
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "crypto_currency_database"
                    ).build()
                }

                return instance
            }
        }
    }
}