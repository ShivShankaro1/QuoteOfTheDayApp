package com.example.quoteofthedayapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: QuoteEntity)

    @Delete
    suspend fun deleteQuote(quote: QuoteEntity)

    // ✅ Show most recent quotes first
    @Query("SELECT * FROM quotes ORDER BY id DESC")
    fun getAllFavoriteQuotes(): Flow<List<QuoteEntity>>

    @Query("SELECT * FROM quotes WHERE text = :quoteText AND author = :author LIMIT 1")
    suspend fun getQuoteByTextAndAuthor(quoteText: String, author: String): QuoteEntity?
}
