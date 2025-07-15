@file:OptIn(
    kotlinx.serialization.ExperimentalSerializationApi::class,
    kotlinx.serialization.InternalSerializationApi::class
)

package com.example.quoteofthedayapp.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitInstance {

    private val json = Json {
        ignoreUnknownKeys = true // Prevents crash from unknown fields
    }

    private val contentType = "application/json".toMediaType()

    val api: QuoteApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://zenquotes.io/api/") // ✅ Updated to ZenQuotes
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(QuoteApiService::class.java)
    }
}
