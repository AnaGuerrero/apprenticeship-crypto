package com.example.cryptochallenge.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptochallenge.data.repository.CryptoRepository
import com.example.cryptochallenge.domain.availablebook.Payload
import com.example.cryptochallenge.ui.commons.SingleLiveEvent
import com.example.cryptochallenge.usecases.GetAvailableBooks
import com.example.cryptochallenge.usecases.GetLocalAvailableBooks
import com.example.cryptochallenge.usecases.SaveLocalAvailableBooks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for home fragment
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val cryptoRepository: CryptoRepository) :
    ViewModel() {

    /**
     * LiveData for event trigger
     */
    private val _eventTrigger = SingleLiveEvent<HomeEvent>()
    val eventTrigger: LiveData<HomeEvent> get() = _eventTrigger

    /**
     * Get available books list
     *
     * @return [LiveData] with web service response
     */
    fun getAvailableBooks(): LiveData<List<Payload>?> {
        return GetAvailableBooks(cryptoRepository).execute()
    }

    /**
     * When [bookList] has information it save it, otherwise it shows local info
     *
     * @param bookList Book's list
     */
    fun processInfo(bookList: List<Payload>?) {
        if (!bookList.isNullOrEmpty())
            viewModelScope.launch {
                SaveLocalAvailableBooks(cryptoRepository).execute(bookList)
            }
        else
            getLocalAvailableBooks()
    }

    /**
     * Get local available books list
     *
     * @return [LiveData] with local available books list
     */
    fun getLocalAvailableBooks(): LiveData<List<Payload>?> {
        return GetLocalAvailableBooks(cryptoRepository).execute()
    }

    /**
     * Trigger event that show available book detail
     *
     * @param cryptoName Available book name
     */
    fun showCryptoDetail(cryptoName: String) {
        if (cryptoName.isNotEmpty())
            _eventTrigger.value = HomeEvent.OnShowCryptoDetail(cryptoName)
    }

    /**
     * Clean disposable property
     */
    fun clear() {
        cryptoRepository.clear()
    }
}