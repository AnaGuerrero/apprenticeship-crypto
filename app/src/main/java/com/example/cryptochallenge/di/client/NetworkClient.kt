package com.example.cryptochallenge.di.client

import com.example.cryptochallenge.data.services.CryptoDetailServices
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.userAgent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Handle the network client
 */
class NetworkClient {
    companion object {
        /**
         * Base url for perform web service calls
         */
        private const val BASE_URL = "https://api.bitso.com/v3/"
    }

    /**
     * Create and return a handler for web service calls
     *
     * @return Instance of [CryptoDetailServices]
     */
    fun getCryptoDetailService(): CryptoDetailServices {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(getOkHttpLogging().build())
            .build()

        return retrofit.create(CryptoDetailServices::class.java)
    }

    /**
     * Create an OkHttpClient Builder with a header
     *
     * @return OkHttpClient Builder Instance
     */
    private fun getOkHttpLogging(): OkHttpClient.Builder {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header("User-Agent", userAgent)
            return@Interceptor chain.proceed(requestBuilder.build())
        })

        return httpClient
    }
}