package com.example.quoteofthedayapp.network

import com.example.quoteofthedayapp.model.QuoteResponse
import retrofit2.http.GET

interface QuoteApiService {

    @GET("random")
    suspend fun getRandomQuote(): List<QuoteResponse>

    @GET("quotes")
    suspend fun getMultipleQuotes(): List<QuoteResponse>

}
