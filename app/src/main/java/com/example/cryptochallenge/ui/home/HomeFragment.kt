package com.example.cryptochallenge.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptochallenge.R
import com.example.cryptochallenge.databinding.FragmentHomeBinding
import com.example.cryptochallenge.domain.availablebook.Payload
import com.example.cryptochallenge.ui.Extensions.show
import com.example.cryptochallenge.ui.home.adapter.CryptocurrencyAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment to show available books list
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    /**
     * Property that represent fragment' view
     */
    private var binding: FragmentHomeBinding? = null

    /**
     * Property that represent viewModel
     */
    private val viewModel by viewModels<HomeViewModel>()

    /**
     * Property that represent available books adapter
     */
    private val cryptoCurrencyAdapter = CryptocurrencyAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        setViewModelListener()
    }

    /**
     * Set [viewModel] listeners
     */
    private fun setViewModelListener() {
        viewModel.getAvailableBooks().observe(viewLifecycleOwner) {
            viewModel.processInfo(it)
            if (!it.isNullOrEmpty())
                setAvailableBooks(it)
        }

        viewModel.getLocalAvailableBooks().observe(viewLifecycleOwner) {
            processInfo(it)
        }

        viewModel.eventTrigger.observe(viewLifecycleOwner) {
            when (it) {
                is HomeEvent.OnShowCryptoDetail -> toCryptoDetail(it.cryptoName)
            }
        }
    }

    /**
     * When [bookList] has information it shows it, otherwise it shows an empty state
     *
     * @param bookList Book's list
     */
    private fun processInfo(bookList: List<Payload>?) {
        if (!bookList.isNullOrEmpty())
            setAvailableBooks(bookList)
        else
            showEmptyState(true)
        showLoader(false)
    }

    /**
     * Set up view elements
     */
    private fun setUpView() {
        binding?.rvCryptocurrencyList?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cryptoCurrencyAdapter
        }

        cryptoCurrencyAdapter.onItemSelect = { cryptoName ->
            viewModel.showCryptoDetail(cryptoName)
        }
    }

    /**
     * Set available books in adapter
     *
     * @param cryptoCurrencyList Available books list
     */
    private fun setAvailableBooks(cryptoCurrencyList: List<Payload>) {
        showEmptyState(false)
        cryptoCurrencyAdapter.submitList(cryptoCurrencyList)
        cryptoCurrencyAdapter.notifyDataSetChanged()
    }

    /**
     * Show or hide empty state
     *
     * @param show Indicator that determines if the empty state should be shown or hidden
     */
    private fun showEmptyState(show: Boolean) {
        binding?.rvCryptocurrencyList?.show(!show)
        binding?.tvEmptyState?.show(show)
    }

    /**
     * Show or hide the loader
     *
     * @param show Indicator that determines if the loader should be shown or hidden
     */
    private fun showLoader(show: Boolean) {
        binding?.iLoader?.root?.show(show)
    }

    /**
     * Show available book detail
     *
     * @param cryptoName Available book name
     */
    private fun toCryptoDetail(cryptoName: String) {
        val bundle = bundleOf(CRYPTO_NAME to cryptoName)
        findNavController().navigate(R.id.toCryptoDetail, bundle)
    }

    override fun onDestroy() {
        viewModel.clear()
        super.onDestroy()
    }

    companion object {
        /**
         * Property to send and set available book
         */
        const val CRYPTO_NAME = "cryptoName"
    }
}