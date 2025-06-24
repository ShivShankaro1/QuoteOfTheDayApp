package com.example.quoteofthedayapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteofthedayapp.data.QuoteEntity
import com.example.quoteofthedayapp.data.QuoteRepository
import com.example.quoteofthedayapp.model.Quote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class QuoteViewModel(
    private val repository: QuoteRepository
) : ViewModel() {

    // Static quote list
    private val quotes = listOf(
        Quote("Believe you can and you're halfway there.", "Theodore Roosevelt"),
        Quote("Do one thing every day that scares you.", "Eleanor Roosevelt"),
        Quote("Your time is limited, don’t waste it living someone else’s life.", "Steve Jobs"),
        Quote("Be yourself; everyone else is already taken.", "Oscar Wilde"),
        Quote("Stay hungry. Stay foolish.", "Steve Jobs")
    )

    // Current quote
    private var index = quotes.indices.random()
    val currentQuote = mutableStateOf(quotes[index])

    // Favorite quotes loaded from Room
    private val _favoriteQuotes = MutableStateFlow<List<Quote>>(emptyList())
    val favoriteQuotes: StateFlow<List<Quote>> = _favoriteQuotes

    init {
        // Observe database for changes to favorite quotes
        viewModelScope.launch {
            repository.getAllFavorites()
                .map { entities -> entities.map { it.toQuote() } }
                .collect { _favoriteQuotes.value = it }
        }
    }

    // Next quote in list
    fun nextQuote() {
        index = (index + 1) % quotes.size
        currentQuote.value = quotes[index]
    }

    // Save current quote to favorites if not already saved
    fun saveFavorite() {
        val current = currentQuote.value
        viewModelScope.launch {
            val exists = repository.isFavorite(current.text, current.author)
            if (!exists) {
                repository.addFavorite(current.toEntity())
            }
        }
    }

    // Remove a quote from favorites
    fun removeFavorite(quote: Quote) {
        viewModelScope.launch {
            repository.removeFavorite(quote.toEntity())
        }
    }

    // Convert Quote to Entity
    private fun Quote.toEntity() = QuoteEntity(
        text = this.text,
        author = this.author,
        isFavorite = true
    )

    // Convert Entity to Quote
    private fun QuoteEntity.toQuote() = Quote(
        text = this.text,
        author = this.author
    )
}
