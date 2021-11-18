package com.example.cryptochallenge.ui

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Extensions for view elements
 */
object Extensions {
    /**
     * Represents major currency
     */
    const val MAJOR = "major"

    /**
     * Represents minor currency
     */
    const val MINOR = "minor"

    /**
     * Represent record divisor
     */
    const val DIVISOR = "_"

    /**
     * Represent number of currencies per book
     */
    const val NUM_CURRENCY_PER_BOOK = 2

    /**
     * Load an currency image and set in an [ImageView]
     *
     * @param imgId ID's image
     */
    fun ImageView.loadCurrencyImg(imgId: Int) {
        Glide.with(this).load(imgId).circleCrop().into(this)
    }

    /**
     * Split available book name and return values
     *
     * @return [Map] with currency names or null if error
     */
    fun String.getMajorAndMinor(): Map<String, String>? {
        val value = split(DIVISOR)
        return when {
            value.isNullOrEmpty() -> null
            value.size == NUM_CURRENCY_PER_BOOK -> mapOf(
                MAJOR to value.first(),
                MINOR to value.last()
            )
            else -> null
        }
    }

    /**
     * Function to show or hide a view
     *
     * @param show Indicator that determines if the loader should be shown or hidden
     */
    fun View.show(show: Boolean) {
        visibility = if (show) View.VISIBLE else View.GONE
    }
}