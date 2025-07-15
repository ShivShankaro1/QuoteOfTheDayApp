package com.example.quoteofthedayapp.data

import android.util.Log
import com.example.quoteofthedayapp.model.QuoteResponse
import com.example.quoteofthedayapp.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class QuoteRepository(private val quoteDao: QuoteDao) {

    suspend fun fetchRandomQuote(): QuoteResponse {
        return try {
            val quoteList = RetrofitInstance.api.getRandomQuote()
            quoteList.firstOrNull() ?: QuoteResponse("No quote found", "Unknown")
        } catch (e: Exception) {
            Log.e("QuoteRepository", "Failed to fetch quote", e) // improved logging
            QuoteResponse("Failed to fetch quote", "Error")
        }
    }

    suspend fun fetchMultipleQuotes(): List<QuoteResponse> {
        return try {
            RetrofitInstance.api.getMultipleQuotes()
        } catch (e: Exception) {
            Log.e("QuoteRepository", "Failed to fetch batch of quotes", e)
            emptyList()
        }
    }

    suspend fun addFavorite(quote: QuoteEntity) {
        quoteDao.insertQuote(quote)
    }

    suspend fun removeFavorite(quote: QuoteEntity) {
        quoteDao.deleteQuote(quote)
    }

    fun getAllFavorites(): Flow<List<QuoteEntity>> {
        return quoteDao.getAllFavoriteQuotes()
    }

    suspend fun isFavorite(text: String, author: String): Boolean {
        return quoteDao.getQuoteByTextAndAuthor(text, author) != null
    }

}
