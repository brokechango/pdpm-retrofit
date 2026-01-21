package com.brokechango.retrofit.datamanager.repository

import com.brokechango.retrofit.datamanager.Retrofit
import com.brokechango.retrofit.models.KanyeQuotes

class KanyeQueryRepository {
    suspend fun getQuote(): KanyeQuotes {
        return Retrofit.retrofit.getQuote()
    }
}