package com.fullmvvmsample.baiju.data.network.responses


import com.fullmvvmsample.baiju.data.db.entities.Quote

// JSON obj converted to Model
data class QuoteResponse(
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)