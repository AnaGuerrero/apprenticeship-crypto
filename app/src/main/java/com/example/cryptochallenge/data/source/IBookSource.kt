package com.example.cryptochallenge.data.source

import androidx.lifecycle.LiveData
import com.example.cryptochallenge.di.database.entity.Book
import com.example.cryptochallenge.domain.availablebook.Payload

/**
 * Handler local calls for the book's table
 */
interface IBookSource {

    /**
     * Insert book in book's table
     *
     * @param book Book to insert
     */
    fun insertBook(book: Book)

    /**
     * Insert a book's list in book's table
     *
     * @param bookList Book's list
     */
    fun insertBookList(bookList: List<Payload>)

    /**
     * Get a book by its name
     *
     * @param bookName Is the book name
     *
     * @return Live data with book info
     */
    fun getBookByName(bookName: String): LiveData<Payload?>

    /**
     * Get all books saved locally
     *
     * @return Live data with list of available books
     */
    fun getAllBooks(): LiveData<List<Payload>?>

    /**
     * Clean the disposable property
     */
    fun clear()
}