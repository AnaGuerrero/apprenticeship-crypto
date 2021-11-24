package com.example.cryptochallenge.di.source

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cryptochallenge.data.source.IBookSource
import com.example.cryptochallenge.di.database.AppDatabase
import com.example.cryptochallenge.di.database.entity.Book
import com.example.cryptochallenge.domain.availablebook.Payload
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Perform calls to book's table of app database
 *
 * @param context Application context
 */
class BookSource(context: Context) : IBookSource {
    /**
     * Property to handle book's table
     */
    private val bookDao = AppDatabase.getInstance(context).bookDAO

    /**
     * Object that dispose observable calls
     */
    private val disposable = CompositeDisposable()

    override fun insertBook(book: Book) {
        val oldBook = bookDao.getBookByName(book.name)
        if (oldBook != null)
            deleteBook(oldBook)
        bookDao.insertBook(book)
    }

    override fun insertBookList(bookList: List<Payload>) {
        bookList.forEach {
            insertBook(Book.toEntity(it))
        }
    }

    override fun getBookByName(bookName: String): LiveData<Payload?> {
        val bookLD = MutableLiveData<Payload?>()

        disposable.add(
            bookDao.getBookByNameThread(bookName)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    bookLD.postValue(Book.toModel(it))
                }, {
                    bookLD.postValue(null)
                })
        )

        return bookLD
    }

    override fun getAllBooks(): LiveData<List<Payload>?> {
        val bookListLD = MutableLiveData<List<Payload>?>()

        disposable.add(
            bookDao.getAllBooks()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    val payloadList = mutableListOf<Payload>()
                    list.forEach { item ->
                        payloadList.add(Book.toModel(item))
                    }
                    bookListLD.postValue(payloadList)
                }, {
                    bookListLD.postValue(null)
                })
        )

        return bookListLD
    }

    /**
     * Delete a book from Book's table
     *
     * @param book Book to delete
     */
    private fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }

    override fun clear() {
        disposable.clear()
    }
}