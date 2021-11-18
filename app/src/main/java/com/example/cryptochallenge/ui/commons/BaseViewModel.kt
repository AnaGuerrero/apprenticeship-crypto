package com.example.cryptochallenge.ui.commons

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cryptochallenge.data.repository.CryptoRepository

/**
 * Base for viewModels
 *
 * @param application Application info
 */
abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Property to handle [CryptoRepository] functions
     */
    protected val cryptoRepository = CryptoRepository(application.applicationContext)
}