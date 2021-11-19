package com.example.cryptochallenge.ui.cryptodetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptochallenge.domain.DetailSectionItem
import com.example.cryptochallenge.domain.SectionType
import com.example.cryptochallenge.domain.ticker.Payload
import com.example.cryptochallenge.ui.commons.BaseViewModel
import com.example.cryptochallenge.ui.commons.SingleLiveEvent
import com.example.cryptochallenge.usecases.*
import kotlinx.coroutines.launch

/**
 * ViewModel for Cryptocurrency detail
 */
class CryptoDetailViewModel(application: Application) : BaseViewModel(application) {

    /**
     * LiveData for load sections
     */
    private val _sections = SingleLiveEvent<MutableList<DetailSectionItem>>()
    val sections: LiveData<MutableList<DetailSectionItem>> get() = _sections

    /**
     * LiveData for show or hide loader
     */
    private val _showLoader = SingleLiveEvent<Boolean>()
    val showLoader: LiveData<Boolean> get() = _showLoader

    private val _localTicker = SingleLiveEvent<Payload?>()
    val localTicker: LiveData<Payload?> get() = _localTicker

    private var bookName: String = ""

    fun start(_bookName: String) {
        bookName = _bookName
    }

    /**
     * Get ticker information of a specific book
     *
     * @param bookName Book name
     * @return [LiveData] with webservice response
     */
    fun getTicker(bookName: String): LiveData<Payload?> {
        return GetTicker(cryptoRepository).execute(bookName)
    }

    fun processTickerInfo(ticker: Payload?) {
        if (ticker != null) {
            setItem(ticker)
            viewModelScope.launch {
                SaveLocalTicker(cryptoRepository).execute(ticker)
            }
        } else {
            getLocalTicker()
        }
    }

    fun getLocalTicker() {
        viewModelScope.launch {
            val ticker = GetLocalTicker(cryptoRepository).execute(bookName)
            _localTicker.postValue(ticker)
        }
    }

    /**
     * Get Order book information of a specific book
     *
     * @param bookName Book name
     * @return [LiveData] with webservice response
     */
    fun getOrderBook(bookName: String): LiveData<com.example.cryptochallenge.domain.orderbook.Payload?> {
        return GetOrderBook(cryptoRepository).execute(bookName)
    }

    fun processOrderbookInfo(payload: com.example.cryptochallenge.domain.orderbook.Payload?) {
        if (payload != null) {
            setItem(payload)
            viewModelScope.launch {
                if (!payload.asks.isNullOrEmpty())
                    SaveLocalOrderList(cryptoRepository).execute(
                        payload.asks,
                        SectionType.ASK.name.lowercase()
                    )

                if (!payload.bids.isNullOrEmpty())
                    SaveLocalOrderList(cryptoRepository).execute(
                        payload.bids,
                        SectionType.BID.name.lowercase()
                    )
            }
        } else {
            getLocalOrderBooks()
        }
    }

    private fun getLocalOrderBooks() {
        viewModelScope.launch {
            val askList = GetLocalOrderList(cryptoRepository).execute(
                bookName,
                SectionType.ASK.name.lowercase()
            )
            val bidList = GetLocalOrderList(cryptoRepository).execute(
                bookName,
                SectionType.BID.name.lowercase()
            )
            val payload = com.example.cryptochallenge.domain.orderbook.Payload(askList, bidList)
            setItem(payload)
        }
    }

    /**
     * Process section information to show it
     *
     * @param item Section information
     */
    fun setItem(item: Any?) {
        when (item) {
            is String -> addSection(DetailSectionItem(SectionType.HEADER, item))
            is Payload -> addSection(DetailSectionItem(SectionType.TICKER, item))
            is com.example.cryptochallenge.domain.orderbook.Payload -> {
                if (!item.asks.isNullOrEmpty())
                    addSection(DetailSectionItem(SectionType.ASK, item.asks))

                if (!item.bids.isNullOrEmpty())
                    addSection(DetailSectionItem(SectionType.BID, item.bids))
            }
        }
    }

    /**
     * Add section information to section list
     *
     * @param section Section information
     */
    private fun addSection(section: DetailSectionItem) {
        var sections = _sections.value
        if (sections.isNullOrEmpty()) {
            sections = mutableListOf()
            sections.add(section)
        } else {
            val index = sections.indexOfFirst {
                it.type == section.type
            }
            if (index in sections.indices) {
                sections.removeAt(index)
                sections.add(index, section)
            } else {
                when (section.type) {
                    SectionType.HEADER -> sections.add(0, section)
                    SectionType.TICKER -> {
                        val index = getIndexOfSectionByType(SectionType.HEADER, sections)
                        if (index in sections.indices) {
                            sections.add(index + 1, section)
                        } else {
                            sections.add(0, section)
                        }
                    }
                    SectionType.ASK -> {
                        var index = getIndexOfSectionByType(SectionType.TICKER, sections)
                        if (index in sections.indices) {
                            sections.add(index + 1, section)
                        } else {
                            index = getIndexOfSectionByType(SectionType.HEADER, sections)
                            if (index in sections.indices) {
                                sections.add(index + 1, section)
                            } else {
                                sections.add(0, section)
                            }
                        }
                    }
                    SectionType.BID -> sections.add(section)
                }
            }
        }
        _sections.value = sections

        if (section.type != SectionType.HEADER)
            _showLoader.value = false
    }

    /**
     * Get index of a section by section type
     *
     * @param type Section type
     * @param sections Section list
     * @return [Int] that represent section index
     */
    private fun getIndexOfSectionByType(type: SectionType, sections: List<DetailSectionItem>) =
        sections.indexOfFirst {
            it.type == type
        }

    /**
     * Clean disposable property
     */
    fun clean() {
        cryptoRepository.clear()
    }
}