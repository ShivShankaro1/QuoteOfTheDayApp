package com.example.quoteofthedayapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteofthedayapp.data.QuoteEntity
import com.example.quoteofthedayapp.data.QuoteRepository
import com.example.quoteofthedayapp.model.QuoteResponse
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class QuoteViewModel(
    private val repository: QuoteRepository
) : ViewModel() {

    private val _quote = MutableStateFlow(
        QuoteResponse("Tap 'Next Quote' to begin.", "QuoteOfTheDayApp")
    )
    val quote: StateFlow<QuoteResponse> = _quote

    private val _favoriteQuotes = MutableStateFlow<List<QuoteResponse>>(emptyList())
    val favoriteQuotes: StateFlow<List<QuoteResponse>> = _favoriteQuotes

    private val _searchResults = MutableStateFlow<List<QuoteResponse>>(emptyList())
    val searchResults: StateFlow<List<QuoteResponse>> = _searchResults

    // ✅ Add isSearching StateFlow
    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    // 🔁 Queue to cache quotes
    private val quoteQueue = mutableListOf<QuoteResponse>()

    init {
        viewModelScope.launch {
            repository.getAllFavorites()
                .map { entities -> entities.map { it.toQuoteResponse() } }
                .collect { _favoriteQuotes.value = it }
        }

        getNextQuote()
    }

    fun getNextQuote() {
        viewModelScope.launch {
            if (quoteQueue.isNotEmpty()) {
                _quote.value = quoteQueue.removeAt(0)
            } else {
                try {
                    val quotes = repository.fetchMultipleQuotes()
                    if (quotes.isNotEmpty()) {
                        quoteQueue.addAll(quotes.drop(1))
                        _quote.value = quotes.first()
                    } else {
                        _quote.value = QuoteResponse("No quotes available", "ZenQuotes")
                    }
                } catch (_: Exception) {
                    _quote.value = QuoteResponse("Failed to fetch quote", "Error")
                }
            }
        }
    }

    fun searchQuotes(query: String) {
        viewModelScope.launch {
            val results = _favoriteQuotes.value.filter {
                it.q.contains(query, ignoreCase = true) || it.a.contains(query, ignoreCase = true)
            }
            _searchResults.value = results
        }
    }


    fun saveFavorite() {
        val current = _quote.value
        viewModelScope.launch {
            val exists = repository.isFavorite(current.q, current.a)
            if (!exists) {
                repository.addFavorite(current.toEntity())
            }
        }
    }

    fun removeFavorite(quote: QuoteResponse) {
        viewModelScope.launch {
            repository.removeFavorite(quote.toEntity())
        }
    }

    private fun QuoteResponse.toEntity() = QuoteEntity(
        text = this.q,
        author = this.a,
        isFavorite = true
    )

    private fun QuoteEntity.toQuoteResponse() = QuoteResponse(
        q = this.text,
        a = this.author
    )
}
