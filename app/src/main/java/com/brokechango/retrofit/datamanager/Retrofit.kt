package com.brokechango.retrofit.datamanager

import com.brokechango.retrofit.datamanager.remote.KanyeQuotesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Retrofit {
    private const val BASE_URL = "https://api.kanye.rest/"

    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KanyeQuotesApi::class.java)
    }
}