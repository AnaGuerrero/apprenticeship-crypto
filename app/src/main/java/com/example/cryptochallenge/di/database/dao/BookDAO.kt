package com.example.cryptochallenge.di.database.dao

import androidx.room.*
import com.example.cryptochallenge.di.database.entity.Book
import io.reactivex.rxjava3.core.Flowable

/**
 * Book's table data access object
 */
@Dao
interface BookDAO {

    /**
     * Insert book in book's table
     *
     * @param book Book to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Book)

    /**
     * Delete book in book's table
     *
     * @param book Book to delete
     */
    @Delete
    fun deleteBook(book: Book)

    /**
     * Get a book by its name
     *
     * @param bookName Is the book name
     *
     * @return Book info
     */
    @Query("select * from book_table where name == :bookName")
    fun getBookByName(bookName: String): Book?

    /**
     * Get a book by its name
     *
     * @param bookName Is the book name
     *
     * @return Observable for get book info
     */
    @Query("select * from book_table where name == :bookName")
    fun getBookByNameThread(bookName: String): Flowable<Book>

    /**
     * Get all books saved locally
     *
     * @return Observable for get book's list
     */
    @Query("select * from book_table")
    fun getAllBooks(): Flowable<List<Book>>
}