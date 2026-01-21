package com.brokechango.retrofit.datamanager.remote

import com.brokechango.retrofit.models.KanyeQuotes
import retrofit2.http.GET

interface KanyeQuotesApi{
    @GET("/")
    suspend fun getQuote(): KanyeQuotes
}