package com.example.quoteofthedayapp.data

import kotlinx.coroutines.flow.Flow

class QuoteRepository(private val quoteDao: QuoteDao) {

    // ✅ Add a quote to favorites
    suspend fun addFavorite(quote: QuoteEntity) {
        quoteDao.insertQuote(quote)
    }

    // ✅ Remove a quote from favorites
    suspend fun removeFavorite(quote: QuoteEntity) {
        quoteDao.deleteQuote(quote)
    }

    // ✅ Get all favorite quotes as a Flow
    fun getAllFavorites(): Flow<List<QuoteEntity>> {
        return quoteDao.getAllFavoriteQuotes()
    }

    // ✅ Check if quote already exists
    suspend fun isFavorite(text: String, author: String): Boolean {
        return quoteDao.getQuoteByTextAndAuthor(text, author) != null
    }
}
