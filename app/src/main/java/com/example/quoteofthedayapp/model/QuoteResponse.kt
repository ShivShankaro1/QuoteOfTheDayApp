@file:OptIn(kotlinx.serialization.InternalSerializationApi::class)

package com.example.quoteofthedayapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuoteResponse(
    @SerialName("q") val q: String,
    @SerialName("a") val a: String
)
